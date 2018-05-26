package com.sanbang.accountSafe.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.accountSafe.service.AccountSafeService;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.dao.ezs_userinfoMapper;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.DateUtils;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.SendMobileMessage;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;


@Service("accountSafeService")
public class AccountSafeServiceImpl implements AccountSafeService {
	
	//日志
	private static Logger log = Logger.getLogger(AccountSafeServiceImpl.class.getName());

	private static int STATUSSUCCESS = 2;  //ezs_store中表示status字段中的：审核通过
	private static long AUDITINGUSERTYPE3 = 3; //ezs_store中表示auditingusertype_id = 3, 认证用户 
	private static long AUDITINGUSERTYPE4 = 4;  //ezs_store中表示auditingusertype_id = 4, 激活用户
	
	@Autowired
	private ezs_userinfoMapper  ezs_userinfoMapper;
	
	
	@Override
	public Result checkAccountStatus(HttpServletRequest request) {

		Result result = Result.success();
		Map<String, Object> map = new HashMap<>();
		
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi == null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			result.setSuccess(false);
			return result;
		}
		
		//检查是否实名认证
		ezs_store store = upi.getEzs_store();
		if(STATUSSUCCESS == store.getStatus() && (AUDITINGUSERTYPE3 == store.getAuditingusertype_id() || AUDITINGUSERTYPE4 == store.getAuditingusertype_id())){
			map.put("userNameCheck", true);
		}else{
			map.put("userNameCheck", false);
		}
		
		//检查邮箱是否验证
		ezs_userinfo info = upi.getEzs_userinfo();
		if(StringUtils.isNotBlank(info.getEmail())){
			map.put("emailCheck", true);
		}else{
			map.put("emailCheck", false);
		}
		result.setObj(map);
		return result;
		
	}
	
	
    /**
     * 获取到密保手机
     */
	@Override
	public Result getSecuratePhone(HttpServletRequest request) {
		
		Result result = Result.success();
		Map<String,Object> map = new HashMap<>();
		
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi == null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			result.setSuccess(false);
			return result;
		}
		
		//密保手机显示 ezs_userinfo 中的phone 字段，如果没有显示 ezs_user 中的 name 字段 还没有 显示 “”
		ezs_userinfo userInfo = upi.getEzs_userinfo();
		if(StringUtils.isNotBlank(userInfo.getPhone())){
			map.put("phone", userInfo.getPhone());
		}else{
			if(StringUtils.isNotBlank(upi.getName())){
				map.put("phone", upi.getName());
			}else{
				map.put("phone", "");
			}
		}
		result.setObj(map);
		
		return result;
	}

	/**
	 * 为新设置的密保手机号码发送短信验证码
	 */
	@Override
	public Result sendCode(String mobile, String code, String mobileascode, String mobilesendcodeexpir,
			String mobileinterval, String mobilesendtimes, Integer flag, String content) {
		
		Result result = Result.failure();
		if(StringUtils.isEmpty(mobile)||!Tools.paramValidate(mobile, 1)){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
			result.setSuccess(false);
			result.setMsg("格式有误，请输入正确的手机号码");
			return result;
		}else{
			
			//先判断发送的次数和时间间隔
			Long totalcodetimes = Long.parseLong(mobilesendtimes);
			RedisResult<Integer> rrtin = (RedisResult<Integer>)RedisUtils.get(mobile+mobileascode, Integer.class);
			Integer codetimes = 0;
			//先判断发送的次数
			if(rrtin.getCode()==RedisConstants.SUCCESS){
				codetimes=rrtin.getResult();
			}
			if(codetimes<totalcodetimes){
				//判断时间间隔
				Long expire = Long.parseLong(mobilesendcodeexpir);
				Long codeinterval = Long.parseLong(mobileinterval);
				RedisResult<Long> reexpirresult = (RedisResult<Long>) RedisUtils.getExpir(mobile+mobileascode);
				Long reexpir=0l;
				if(reexpirresult.getCode() == RedisConstants.SUCCESS){
					reexpir=reexpirresult.getResult();
				}
				if(expire-reexpir>codeinterval){
					//间隔大于60S可以发送短信
					//短信内容
					log.info("短信验证码发送内容："+content);
					
					try {
						SendMobileMessage.sendMsg(mobile, content);
						result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						result.setSuccess(true);
						result.setObj(new HashMap<>().put("mobile", mobile));
						result.setMsg("验证码发送成功");
						RedisUtils.set(mobile+mobileascode, code, Long.parseLong(mobilesendcodeexpir));
						RedisUtils.set(mobile+mobileascode+"times", ++codetimes, DateUtils.getTimeValue());
					} catch (Exception e) {
						e.printStackTrace();
						log.error("短信验证码功能失败");
						log.error(e.toString());
						result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						result.setSuccess(false);
						result.setMsg("服务器异常");
					}
				}else{
					result.setErrorcode(DictionaryCode.ERROR_WEB_GET_VEIFY_CODE_ERROR);
					result.setSuccess(false);
					result.setMsg("请等待"+mobileinterval+"s后再次点击");
				}
				
				
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_GET_CODE_LIMIT);
				result.setSuccess(false);
				result.setMsg("验证码已发送多次，请查收短信");
			}
		}
		
		
		return result;
	}

	
	/**
	 * 设置或者修改密保手机号码
	 */
	@Override
	public Result setOrUpdateSecuratePhone(String mobile, String code, HttpServletRequest request) {
		Result result = Result.success();
		
		if(StringUtils.isEmpty(mobile)||!Tools.paramValidate(mobile, 1)){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
			result.setSuccess(false);
			result.setMsg("格式有误，请输入正确的手机号码");
			return result;
		}else{
			
			boolean b = ckcodemobile(code,mobile,"ASCODE");
			if(!b){
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("短信验证码错误");
			}else{
				//判断登录拿用户信息
				ezs_user upi = RedisUserSession.getLoginUserInfo(request);
				if(upi == null){
					result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
					result.setMsg("用户未登录");
					result.setSuccess(false);
					return result;
				}
				
				try {
					//密保手机显示 ezs_userinfo 中的phone 字段，如果没有显示 ezs_user 中的 name 字段 还没有 显示 “”
					ezs_userinfo userInfo = upi.getEzs_userinfo();
					userInfo.setPhone(mobile);
					ezs_userinfoMapper.updateByPrimaryKeySelective(userInfo);
					RedisUtils.del(mobile+"ASCODE");
				} catch (Exception e) {
					e.printStackTrace();
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
					result.setSuccess(false);
					result.setMsg("修改密保手机失败");
				}
				
			}
			
		}
		return result;
	}
	
	/**
	 * 通过标识 检验验证码
	 * @param code 验证码
	 * @param mobile 手机号
	 * @param mobilerecodestr 模块标识
	 * @return
	 */
	public boolean ckcodemobile(String code,String mobile,String mobileascode){
		RedisResult<String> recode = null;
		Map<String,Object> map=new HashMap<>();
		recode = (RedisResult<String>) RedisUtils.get(mobile+mobileascode,String.class);
		log.info("当前用户修改密保手机验证码状态为："+ recode.getCode());
		if(recode!=null&&recode.getCode()==RedisConstants.SUCCESS){
			String mCode=null;
			mCode=recode.getResult();
		if((mCode!=null&&mCode.equals(code.trim()))){
			return true;
		 }
		}
		return false;
	}

}
