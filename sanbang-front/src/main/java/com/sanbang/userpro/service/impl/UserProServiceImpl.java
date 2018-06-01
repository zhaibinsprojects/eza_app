package com.sanbang.userpro.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_companyType_dict;
import com.sanbang.bean.ezs_industry_dict;
import com.sanbang.bean.ezs_position;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_user_role;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.dao.ezs_industry_dictMapper;
import com.sanbang.dao.ezs_positionMapper;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.DateUtils;
import com.sanbang.utils.EzaishengUCException;
import com.sanbang.utils.MD5Util;
import com.sanbang.utils.RandomStr32;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.SendMobileMessage;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.LinkUserVo;
import com.sanbang.vo.MessageDictionary;

/**
 * 用户相关处理
 * 
 * @author
 * 
 * 		2016年7月28日
 */
@Service("userProService")
public class UserProServiceImpl implements UserProService {

	// 日志
	private static Logger log = Logger.getLogger(UserProServiceImpl.class.getName());

	@Resource(name = "ezs_userinfoMapper")
	private com.sanbang.dao.ezs_userinfoMapper ezs_userinfoMapper;

	// 用户登陆信息
	@Resource(name = "ezs_userMapper")
	private ezs_userMapper ezs_userMapper;

	// 商铺
	@Resource(name = "ezs_storeMapper")
	private ezs_storeMapper ezs_storeMapper;

	// 关于用户公司类型存储
	@Resource(name = "ezs_companyType_dictMapper")
	private com.sanbang.dao.ezs_companyType_dictMapper ezs_companyType_dictMapper;

	// 关于用户公司主营行业存储
	@Resource(name = "ezs_industry_dictMapper")
	private ezs_industry_dictMapper ezs_industry_dictMapper;

	@Resource(name = "ezs_positionMapper")
	private ezs_positionMapper ezs_positionMapper;

	@Resource(name = "ezs_user_roleMapper")
	private com.sanbang.dao.ezs_user_roleMapper ezs_user_roleMapper;

	@Value("${consparam.cookie.useridcard}")
	private String USERIDCARD;

	// #用户cookie 中uerKey有效期
	@Value("${consparam.cookie.cookieuserkeyexpir}")
	private String cookieuserkeyexpir;

	// rediskey有效期
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;

	// cookie
	@Value("${consparam.cookie.userkey}")
	private String cookieuserkey;

	@Value("${consparam.mobile.sendtimes}")
	private String mobilesendtimes;

	@Value("${consparam.mobile.sendcodeexpir}")
	private String mobilesendcodeexpir;

	@Value("${consparam.mobile.recode}")
	private String mobilerecode;

	@Value("${consparam.mobile.interval}")
	private String mobileinterval;

	@Value("${consparam.mobile.upmocode}")
	private String mobileupmocode;

	@Value("${consparam.mobile.ftcode}")
	private String mobileftcode;

	@Value("${consparam.cookie.usertrailidcard}")
	private String cookieusertrailidcard;

	@Value("${consparam.cookie.userstaticidcard}")
	private String cookieuserstaticidcard;

	@Value("${consparam.reg.passwd}")
	private String randpasswd;

	// 注册阶段标识
	@Value("${consparam.cookie.registcard}")
	private String registcarid;

	/**
	 * 更新缓存中的值
	 * 
	 * @param username
	 * @param mobile
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Object updateUserSessionInfo(String username, String mobile) {
		Map<String, Object> map = new HashMap<>();
		RedisResult<Set<String>> results = (RedisResult<Set<String>>) RedisUtils.getMoHu("*" + username + mobile + "*");
		if (results.getCode() == RedisConstants.SUCCESS) {
			Set<String> sets = results.getResult();
			Set<String> newsets = new HashSet<String>();
			for (String tempstr : sets) {
				if (tempstr.length() > 43) {
					ezs_userinfo userProInfo = null;// ezs_userinfoMapper.getUserInfoByUserName(username.trim());
					if (userProInfo != null) {
						RedisUtils.set(tempstr, userProInfo, Long.parseLong(redisuserkeyexpir));
					}
				}
			}
		}
		map.put("status", "success");
		map.put("message", "ok");
		return map;
	}

	/**
	 * 登录
	 */
	@Override
	public Result login(String userName, String pd, String code, String userAgent, String ip,
			HttpServletRequest request, HttpServletResponse response, Integer flag,String h5orapp) {
		Result result = Result.failure();
		Date date = new Date();
		StringBuilder sbd = new StringBuilder("用户");
		sbd.append(userName).append("请求登陆，passwd=").append(pd).append(" &userAgent=").append(userAgent).append(" &ip=")
				.append(ip).append(" &登录,时间:").append(new SimpleDateFormat("yyyyMMddHHmmss").format(date));
		log.info(sbd.toString());
		String  useridcard="";
		if("app".equals(h5orapp)){
			 useridcard = request.getParameter("token");
		}else{
			 useridcard = RedisUserSession.getUserKey(USERIDCARD, request);
		}
		
		result = loginParam(userName, pd, code, useridcard, request, flag);
		if (result.getSuccess()) {
			loginuser(userName, request, response, result, userAgent, pd, ip, date, flag,h5orapp);
		} else {
			return result;
		}
		log.debug("用户" + userName + "登陆结果" + result.getErrorcode() + "  " + result.getMsg());
		return result;
	}

	/**
	 * 通过标识 检验验证码
	 * 
	 * @param code
	 *            验证码
	 * @param mobile
	 *            手机号
	 * @param mobilerecodestr
	 *            模块标识
	 * @return
	 */
	public boolean ckcodemobile(String code, String mobile, String mobilerecodestr) {
		RedisResult<String> recode = null;
		Map<String, Object> map = new HashMap<>();
		recode = (RedisResult<String>) RedisUtils.get(mobile + mobilerecodestr, String.class);
		if (recode != null && recode.getCode() == RedisConstants.SUCCESS) {
			String mCode = null;
			mCode = recode.getResult();
			if ((mCode != null && mCode.equals(code.trim()))) {
				return true;
			}
		}
		return false;
	}

	Result loginParam(String userName, String pd, String code, String useridcard, HttpServletRequest request,
			Integer flag) {
		Result result = Result.success();

		if (flag == null) {
			// 直接登陆
			if (StringUtils.isEmpty(pd)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("登陆密码不能为空");
			}
			if (StringUtils.isEmpty(userName) || !Tools.paramValidate(userName, 7)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("用户名格式错误");
				;
			}
			if (StringUtils.isEmpty(pd) || MD5Util.md5Encode(pd.trim()).length() != 32) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("密码格式错误");
			}
		} else {
			// 短信验证码
			if (StringUtils.isEmpty(userName) || !Tools.paramValidate(userName, 1)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("手机号码格式错误");
			}
			if (StringUtils.isEmpty(code)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("登陆码不能为空");
			} else {
				boolean b = ckcodemobile(code, userName, "MOBILELOGINFLAG");
				if (!b) {
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("登陆验证码错误");
				}
				RedisUtils.del(userName + "MOBILELOGINFLAG");
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public void loginuser(String userName, HttpServletRequest request, HttpServletResponse response, Result result,
			String userAgent, String pd, String ip, Date date, Integer flag,String h5orapp) {
		ezs_user upi=null;
		if(Tools.isEmpty(request.getParameter("token"))){
			 upi = RedisUserSession.getUserInfoByKeyForApp(request);
		}else{
			 upi = RedisUserSession.getLoginUserInfo(request);
		}
		
		if (upi != null && upi.getName().equals(userName.trim())) {
			Boolean tempStatus = upi.getDeleteStatus();
			if (!tempStatus) {
				result.setSuccess(true);
				result.setMsg("登陆成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
 			} else {
				result.setSuccess(false);
				result.setMsg("登陆失败用户未启用");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			}
		} else {
			if (StringUtils.isEmpty(userAgent)) {
				userAgent = "unknownUserAgent";
			}
			// 根据用户名查询用户的信息
			ezs_user userProInfo = null;
			List<ezs_user> userProInfolist = null;
			userProInfolist = ezs_userMapper.getUserInfoByUserNameFromBack(userName.trim());
			if (userProInfolist == null || userProInfolist.size() == 0) {
				result.setSuccess(false);
				result.setMsg("用户不存在");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			} else {
				userProInfo = userProInfolist.get(0);
				try {
					ezs_bill bill=userProInfo.getEzs_bill();
					if(null==bill){
						userProInfo.setEzs_bill(new ezs_bill());
					}
				} catch (Exception e1) {
					userProInfo.setEzs_bill(new ezs_bill());
				}
				boolean b = false;
				if (flag == null) {
					b = MD5Util.md5Encode(pd).toLowerCase().equals(userProInfo.getPassword());
				} else {
					b = true;
					RedisUtils.del(userName + "MOBILELOGINFLAG");
				}
				// 密码验证正确
				if (b) {
					// 判断是否是财务关联账户,如果是,将登陆信息切换到主账户.avatar:账户登录标识,true:主账户登录,false:财务账户登录,并保存在redis中
					// 如果当前为主账户,更改标识
					if (Tools.isEmpty(String.valueOf(userProInfo.getParent_id()))) {
						userProInfo.setAvatar(true);
					} else {
						// 非主账户,是否有关联的主账户
						userProInfo.setAvatar(false);
					}

					// 判断是否启用
					boolean tempStatus = userProInfo.getDeleteStatus();

					String str32 = RandomStr32.getStr32();
					String userKey =""; 
					if(h5orapp.equals("h5")){
						userKey="h5"+userProInfo.getName() + userProInfo.getEzs_userinfo().getPhone() + str32;
						// 添加缓存
						Cookie cookie = new Cookie(cookieuserkey, userKey);
						cookie.setMaxAge(Integer.parseInt(cookieuserkeyexpir));
						cookie.setPath("/");
						response.addCookie(cookie);
					}else{
						userKey="app"+userProInfo.getName() + userProInfo.getEzs_userinfo().getPhone() + str32;
						Map<String, Object> map=new HashMap<>();
						map.put("token", userKey);
						result.setObj(map);
					}
					
					userProInfo.setUserkey(userKey);
					

					try {
						if (!tempStatus && userProInfo.getAddTime().getTime() > 1479052799000l) {
							// 首次登陆 应该跳转到 注册联系人资料
							result.setSuccess(true);
							result.setMsg("登陆成功");
							result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
							RedisResult<String> rrt;
							rrt = (RedisResult<String>) RedisUtils.set(userKey, userProInfo,
									Long.parseLong(redisuserkeyexpir));
							if (rrt.getCode() == RedisConstants.SUCCESS) {
								log.debug("用户" + userName + "：userKey保存到redis成功执行");
							} else {
								log.debug("用户" + userName + "：userKey保存到redis失败");
							}
						} else {
							result.setSuccess(false);
							result.setMsg("用户状态异常");
							result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
						}
					} catch (Exception e) {
						result.setSuccess(false);
						result.setMsg("系统错误");
						result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						log.info("系统错误", e);
					}
					log.error("IP===========>" + ip);
				} else {
					// 密码错误
					result.setSuccess(false);
					result.setMsg("用户名密码错误");
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				}
			}
		}
	}

	/**
	 * 未登录状态下 恢复到登陆之前的状态
	 */
	public void resumePath(HttpServletRequest request, Map<String, Object> result, HttpServletResponse response) {
		String toStaticURl = RedisUserSession.getUserKey(cookieuserstaticidcard, request);
		result.put("tostaticurl", toStaticURl);
		Cookie cookie = new Cookie(cookieuserstaticidcard, null);
		cookie.setDomain("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

	/**
	 * 注册新用户
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result userAdd(String username, String passwd, String passwdA, String mobile, String code, String myip,
			Integer flag, HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("新用户注册 参数：username=" + username + " &passwd=" + passwd + " &mobile=" + mobile + " &code=" + code+"&passwdA=" + passwdA);
		Result result = Result.failure();

		// 用户名为手机号
		username = mobile;

		if (flag == null) {
			// 用户自己注册
			result = userAddParam(result, username, passwd, passwdA, mobile, code, request);
		} else {
			// 其他注册方式 flag 为4qq注册 5微信注册
			// 生成用户名密码
			result = userAddParam(result, username, passwd, passwdA, mobile, code, request);
			if (flag == 4) {
				username = "qq_" + RandomStr32.getStrDefined(7);
			} else if (flag == 5) {
				username = "wx_" + RandomStr32.getStrDefined(7);
			}
			//passwd = MD5Util.md5Encode(passwd).toLowerCase();
		}
		if (result.getSuccess()) {
			if (flag == null) {
				result = regself(request, response, result, mobile, username, myip, passwd,0);
			} else {
				result = regself(request, response, result, mobile, username, myip, passwd,1);
			}
		}
		log.info("注册结果：code=" + result.getErrorcode() + " &message=" + result.getMsg());
		return result;
	}

	@SuppressWarnings("unchecked")
	Result userAddParam(Result result, String username, String passwd, String passwdA, String mobile, String code,
			HttpServletRequest request) {

		if (StringUtils.isEmpty(passwd) || passwd.trim().length() < 6 || passwd.trim().length() > 20) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("密码格式错误");
			return result;
		}
		if (StringUtils.isEmpty(passwdA)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("确认密码不能为空");
			return result;
		} else {
			if (StringUtils.isNotEmpty(passwd) && !passwd.equals(passwdA)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("两次密码输入不一致");
				return result;
			}
		}
		if (StringUtils.isEmpty(mobile) || !Tools.paramValidate(mobile, 1)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("格式有误，请输入正确的手机号码");
			return result;
		}
		if (StringUtils.isEmpty(code)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("验证码不能为空");
			return result;
		} else {
			RedisResult<String> recode = null;
			recode = (RedisResult<String>) RedisUtils.get(mobile + mobilerecode, String.class);
			if (recode != null && recode.getCode() == RedisConstants.SUCCESS) {
				String mCode = null;
				mCode = recode.getResult();
				if ((mCode != null && mCode.equals(code.trim()))) {
					result.setSuccess(true);
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("验证码错误");
					return result;
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("验证码错误");
				return result;
			}
			try {
				RedisUtils.del(mobile + "RECODE");
			} catch (Exception e1) {
				log.info("删除redis数据失败", e1);
			}
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public Result regself(HttpServletRequest request, HttpServletResponse response, Result result, String mobile,
			String username, String myip, String passwd, int flag) {
		// 用户自注册
		List<ezs_user> upis = ezs_userMapper.getUserInfoByUserNameFromBack(username);
		if (upis != null && upis.size() != 0) {
			// 已存在，不能注册
			result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
			result.setSuccess(false);
			result.setMsg("手机号码已经被注册");
			return result;
		}

		ezs_user upi = new ezs_user();
		upi.setName(username);
		upi.setPassword(MD5Util.md5Encode(passwd));
		upi.setAddTime(new Date());
		upi.setLastLoginDate(new Date());
		upi.setLastLoginIp(myip);
		upi.setLoginCount(1);
		upi.setLoginDate(new Date());
		upi.setLoginIp(myip);
		upi.setName(username);
		upi.setUserRole("BUYER");
		upi.setDeleteStatus(false);

		if (flag == 0) {// 缓存用名称密码
			Cookie[] cookies = request.getCookies();

			RedisResult<String> rrt = null;
			String tempKey = RandomStr32.getStr32() + RandomStr32.getStr32();
			String userKey = RedisUserSession.getUserKey(registcarid, request);

			if (userKey != null) {
				if (cookies != null) {
					for (Cookie ck : cookies) {
						if (ck.getName().equals(registcarid)) {
							userKey = ck.getValue();
							ck.setValue(tempKey);
						}
					}
				}
				Cookie cookie = new Cookie(registcarid, tempKey);
				cookie.setMaxAge(3600);
				response.addCookie(cookie);
				RedisUtils.del(userKey);

				rrt = (RedisResult<String>) RedisUtils.set(tempKey, upi, Long.parseLong(redisuserkeyexpir));
			} else {
				Cookie cookie = new Cookie(registcarid, tempKey);
				response.addCookie(cookie);
				rrt = (RedisResult<String>) RedisUtils.set(tempKey, upi, Long.parseLong(redisuserkeyexpir));
			}

			if (rrt.getCode() == RedisConstants.SUCCESS) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_SUCCESS);
				result.setSuccess(true);
				result.setMsg("请求成功");
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_FAIL);
				result.setSuccess(false);
				result.setMsg("请求失败");
				// hunXinService.regHuanxinSingle(username,
				// HunXinServiceImpl.passwordefault, userInfo.getUserid());
			}

		} else {
			RedisResult<String> rrt = null;
			String tempKey = "SANBANGAPP"+RandomStr32.getStr32() + RandomStr32.getStr32();
			String userKey = request.getParameter("token");
			if (userKey != null) {
				rrt = (RedisResult<String>) RedisUtils.set(userKey, upi, Long.parseLong(redisuserkeyexpir));
			} else {
				Map<String, Object> map=new HashMap<>();
				map.put("token", tempKey);
				result.setObj(map);
				rrt = (RedisResult<String>) RedisUtils.set(tempKey, upi, Long.parseLong(redisuserkeyexpir));
			}

			if (rrt.getCode() == RedisConstants.SUCCESS) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_SUCCESS);
				result.setSuccess(true);
				result.setMsg("请求成功");
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_FAIL);
				result.setSuccess(false);
				result.setMsg("请求失败");
				// hunXinService.regHuanxinSingle(username,
				// HunXinServiceImpl.passwordefault, userInfo.getUserid());
			}
		}

		try {
			RedisUtils.del(mobile + "RECODE");
		} catch (Exception e1) {
			log.info("删除redis数据失败", e1);
		}
		return result;
	}

	/**
	 * 发送注册，登陆短信验证码
	 * 
	 * @param phone
	 * @param code
	 * @param mobilerecodestr
	 *            验证码标识
	 * @param mobilesendcodeexpirstr
	 *            验证码有效期 单位为 秒
	 * @param mobileintervalstr
	 *            验证码距离下一次点击的时间间隔
	 * @param mobilesendtimesstr
	 *            验证码 获取次数 flag=1无密登陆
	 * @return
	 */
	@Override
	public Result sendCode(String phone, String code, String mobilerecodestr, String mobilesendcodeexpirstr,
			String mobileintervalstr, String mobilesendtimesstr, Integer flag, String content) {
		Result result = Result.failure();
		if (StringUtils.isEmpty(phone) || !Tools.paramValidate(phone, 1)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
			result.setSuccess(false);
			result.setMsg("格式有误，请输入正确的手机号码");
			return result;
		} else {
			// 无密登陆
			if (null != flag && flag == 1) {
				List<ezs_user> upis = ezs_userMapper.getUserInfoByUserNameFromBack(phone);
				if (upis == null || upis.size() == 0) {
					// 不存在，不能登陆
					result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
					result.setSuccess(false);
					result.setMsg("无该手机号注册信息，请先注册！");
					return result;
				}
			}

			// 先判断发送的次数 和 时间间隔
			Long totalcodetimes = Long.parseLong(mobilesendtimesstr);
			@SuppressWarnings("unchecked")
			RedisResult<Integer> rrtin = (RedisResult<Integer>) RedisUtils.get(phone + mobilerecodestr + "times",
					Integer.class);
			Integer codetimes = 0;
			// 先判断发送的次数
			if (rrtin.getCode() == RedisConstants.SUCCESS) {
				codetimes = rrtin.getResult();
			}
			if (codetimes < totalcodetimes) {
				// 判断时间间隔
				Long expir = Long.parseLong(mobilesendcodeexpirstr);
				Long codeinterval = Long.parseLong(mobileintervalstr);
				RedisResult<Long> reexpirresult = (RedisResult<Long>) RedisUtils.getExpir(phone + mobilerecodestr);
				Long reexpir = 0l;
				if (reexpirresult.getCode() == RedisConstants.SUCCESS) {
					reexpir = reexpirresult.getResult();
				}
				if (expir - reexpir > codeinterval) {
					// 间隔大于60 可以发送短信
					// 短信内容
					log.info("短信验证码,发送内容:" + content);
					try {
						SendMobileMessage.sendMsg(phone, content);
						result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						result.setSuccess(true);
						Map<String, Object> map = new HashMap<>();
						map.put("mobile", phone);
						result.setMsg("验证码发送成功");
						result.setObj(map);
						RedisUtils.set(phone + mobilerecodestr, code, Long.parseLong(mobilesendcodeexpirstr));
						RedisUtils.set(phone + mobilerecodestr + "times", ++codetimes, DateUtils.getTimeValue());
					} catch (Exception e) {
						log.error("短信验证码功能失败");
						log.error(e.toString());
						result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						result.setSuccess(false);
						result.setMsg("服务器异常");
					}
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_GET_VEIFY_CODE_ERROR);
					result.setSuccess(false);
					result.setMsg("请等待" + mobileintervalstr + "s后再次点击");
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_GET_CODE_LIMIT);
				result.setSuccess(false);
				result.setMsg("验证码已发送多次，请查收短信");
			}

		}

		return result;
	}

	/**
	 * 发送修改手机号码验证码
	 */
	@Override
	public Result sendUpMoCode(String phone, String code) {
		Result result = Result.failure();
		if (StringUtils.isEmpty(phone) || !Tools.paramValidate(phone, 1)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("手机号码格式错误");
		} else {
			int istrue = ezs_userMapper.checkMobile(phone);
			if (istrue == 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
				result.setSuccess(false);
				result.setMsg("未找到该手机号码客户");
				return result;
			}
			
			
			// 先判断发送的次数 和 时间间隔
			Long totalcodetimes = Long.parseLong(mobilesendtimes);
			// 这里需要改
			RedisResult<Integer> rrtin = (RedisResult<Integer>) RedisUtils.get(phone + mobileupmocode + "times",
					Integer.class);
			Integer codetimes = 0;
			// 先判断发送的次数
			if (rrtin.getCode() == RedisConstants.SUCCESS) {
				codetimes = rrtin.getResult();
			}
			if (codetimes < totalcodetimes) {
				// 判断时间间隔
				Long expir = Long.parseLong(mobilesendcodeexpir);
				Long codeinterval = Long.parseLong(mobileinterval);
				RedisResult<Long> reexpirresult = (RedisResult<Long>) RedisUtils.getExpir(phone + mobileupmocode);
				Long reexpir = 0l;
				if (reexpirresult.getCode() == RedisConstants.SUCCESS) {
					reexpir = reexpirresult.getResult();
				}
				if (expir - reexpir > codeinterval) {
					// 间隔大于60 可以发送短信
					// 短信内容
					String content = "您的短信验证码:" + code.toString() + ",请勿告诉他人,有效时间为" + mobilesendcodeexpir + "分钟!";
					log.info("短信验证码,发送内容:" + content);
					try {
						SendMobileMessage.sendMsg(phone, content);
						result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						result.setSuccess(true);
						result.setObj(new HashMap<>().put("mobile", phone));
						result.setMsg("验证码发送成功");
						RedisUtils.set(phone + mobileupmocode, code, Long.parseLong(mobilesendcodeexpir));
						// 这里需要改
						RedisUtils.set(phone + mobileupmocode + "times", ++codetimes, DateUtils.getTimeValue());
					} catch (Exception e) {
						log.error("短信验证码功能失败");
						log.error(e.toString());
						result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						result.setSuccess(false);
						result.setMsg("服务器异常");
					}
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("请等待" + mobileinterval + "s后再次点击");
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("验证码已发送多次，请查收短信");
			}

		}
		return result;
	}

	/**
	 * 发送忘记密码短信验证码
	 */
	@Override
	public Result sendFtCode(String phone, String code) {
		Result result = Result.failure();
		if (StringUtils.isNotEmpty(phone) && Tools.paramValidate(phone, 1)) {
			int istrue = ezs_userMapper.checkMobile(phone);
			if (istrue == 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
				result.setSuccess(false);
				result.setMsg("未找到该手机号码客户");
				return result;
			}
			// 先判断发送的次数 和 时间间隔
			Long totalcodetimes = Long.parseLong(mobilesendtimes);
			// 这里需要改
			RedisResult<Integer> rrtin = (RedisResult<Integer>) RedisUtils.get(phone + mobileftcode + "times",
					Integer.class);
			Integer codetimes = 0;
			// 先判断发送的次数
			if (rrtin.getCode() == RedisConstants.SUCCESS) {
				codetimes = rrtin.getResult();
			}
			if (codetimes < totalcodetimes) {
				// 判断时间间隔
				Long expir = Long.parseLong(mobilesendcodeexpir);
				Long codeinterval = Long.parseLong(mobileinterval);
				RedisResult<Long> reexpirresult = (RedisResult<Long>) RedisUtils.getExpir(phone + mobileftcode);
				Long reexpir = 0l;
				if (reexpirresult.getCode() == RedisConstants.SUCCESS) {
					reexpir = reexpirresult.getResult();
				}
				if (expir - reexpir > codeinterval) {
					// 间隔大于60 可以发送短信
					// 短信内容
					String content = MessageDictionary.upPswCode(code);

					log.info("短信验证码,发送内容:" + content);

					try {
						SendMobileMessage.sendMsg(phone, content);
						result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						result.setSuccess(true);
						result.setObj(new HashMap<>().put("mobile", phone));
						result.setMsg("验证码发送成功");

						RedisUtils.set(phone + mobileftcode, code, Long.parseLong(mobilesendcodeexpir));
						// 这里需要改
						RedisUtils.set(phone + mobileftcode + "times", ++codetimes, DateUtils.getTimeValue());
					} catch (Exception e) {
						log.error("短信验证码功能失败");
						log.error(e.toString());
						result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						result.setSuccess(false);
						result.setMsg("服务器异常");
					}
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("请等待" + mobileinterval + "s后再次点击");
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("验证码已发送多次，请查收短信");
			}
		} else {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("手机号码格式错误");

		}

		return result;
	}

	/**
	 * 通过手机号修改密码
	 * 
	 * @throws EzaishengUCException
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Result chgPasswd(String passwd, String passwdA, HttpServletRequest request) throws EzaishengUCException {
		Result result = Result.failure();
		log.info("修改密码  passwd=" + passwd + " &passwdA=" + passwdA);
		if (StringUtils.isNotEmpty(passwd)) {
			if (StringUtils.isNotEmpty(passwdA)) {
				if (passwd.equals(passwdA)) {
					// 都没问题 应该修改密码
					String doFtCodde = RedisUserSession.getUserKey("DOFTCODE", request);
					@SuppressWarnings("unchecked")
					RedisResult<String> mobileres = (RedisResult<String>) RedisUtils.get(doFtCodde, String.class);
					if (mobileres != null && mobileres.getResult() != null
							&& mobileres.getCode() == RedisConstants.SUCCESS) {
						RedisUtils.del(doFtCodde);
						int a = 0;
						try {
							a = ezs_userMapper.modifyPassword(mobileres.getResult(),
									MD5Util.md5Encode(passwd).toLowerCase());
						} catch (Exception e) {
							log.error("修改密码失败", e);
						}
						if (a >= 1) {
							result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
							result.setSuccess(true);
							result.setMsg("操作成功");
						} else {
							result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
							result.setSuccess(false);
							result.setMsg("操作异常");
							throw new EzaishengUCException("手机号修改异常");
						}
					} else {
						result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
						result.setSuccess(false);
						result.setMsg("操作异常");
					}

				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("密码和确认密码必须相同");
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("确认密码不能为空");
			}
		} else {

			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("密码不能为空");
		}
		log.info("修改密码结果：code=" + result.getErrorcode() + " &passwd=" + result.getMsg());
		return result;
	}

	/**
	 * 检查用户名是否存在
	 */
	@Override
	public Result checkUserName(String userName) {
		Result result = Result.failure();
		log.info("检验账号:userName=" + userName);
		if (StringUtils.isEmpty(userName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入6-20位以内数字或字母!");
		} else if (!Tools.paramValidate(userName, 0)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("账号格式错误");
		} else {
			List<ezs_user> count = null;
			try {
				count = ezs_userMapper.getUserInfoByUserNameFromBack(userName);
				if (count != null && count.size() > 0) {
					// 可以注册
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("账号格式错误");
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					result.setSuccess(true);
					result.setMsg("户名存在");
				}
			} catch (Exception e) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				result.setSuccess(false);
				result.setMsg("系统错误");
			}
		}
		log.info("检验账号结果:code=" + result.getErrorcode() + " &message=" + result.getMsg());
		return result;
	}

	/**
	 * 检查手机号码是否存在
	 */
	@Override
	public Result checkMobile(String mobile) {
		Result result = Result.failure();
		if (StringUtils.isEmpty(mobile) || !Tools.paramValidate(mobile, 1)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("格式有误，请输入正确的手机号码");
		} else {
			int count = 0;
			try {
				count = ezs_userMapper.checkMobile(mobile);
				if (count == 0) {
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					result.setSuccess(true);
					result.setMsg("该手机号可用");
					// 可以注册
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("手机号码已经被注册");
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
				result.setSuccess(false);
				result.setMsg("服务器异常");
			}
		}
		return result;
	}

	/**
	 * 检查修改密码前的验证码(忘记密码模块修改密码)
	 */
	@Override
	public Result checkFtCode(String mobile, String code, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("修改密码前验证验证码：mobile=" + mobile + "  &code=" + code);
		Result result = Result.failure();
		result = checkFtCodeParam(mobile, code);
		if (result.getSuccess()) {
			@SuppressWarnings("unchecked")
			RedisResult<String> ftcode = (RedisResult<String>) RedisUtils.get(mobile + "FTCODE", String.class);
			if (ftcode != null && ftcode.getResult() != null && ftcode.getCode() == RedisConstants.SUCCESS) {
				String temCode = ftcode.getResult();
				if (temCode.trim().equals(code.trim())) {
					// 正确 进入下一步
					if (request != null && response != null) {
						String str32 = RandomStr32.getStr32();
						String key = mobile + str32;
						Cookie cookie = new Cookie("DOFTCODE", key);
						cookie.setMaxAge(-1);
						cookie.setPath("/");
						response.addCookie(cookie);
						RedisResult<String> rrt;
						rrt = (RedisResult<String>) RedisUtils.set(key, mobile, Long.parseLong(mobilesendcodeexpir));
						if (rrt.getCode() == RedisConstants.SUCCESS) {
							log.info("用户" + mobile + "：修改密码前手机号码信息保存到redis成功执行");
						} else {
							log.info("用户" + mobile + "：修改密码前手机号码信息保存到redis失败");
						}
					}
					result.setMsg("操作成功");
					// 删除缓存中验证码
					RedisUtils.del(mobile + "FTCODE");
					RedisUtils.del(mobile + "FTSENDTIME");
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("验证码错误");
				}

			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("验证码失效");
			}
		} else {
			return result;
		}
		return result;
	}

	Result checkFtCodeParam(String mobile, String code) {
		Result result = Result.success();
		if (StringUtils.isEmpty(mobile) || !Tools.paramValidate(mobile, 1)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("手机号码格式错误");
		}
		if (StringUtils.isEmpty(code)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("验证码不能为空");
		}
		return result;
	}

	/**
	 * 修改手机号码
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public Result checkUpMoCode(String mobile, String code, ezs_user upi, HttpServletRequest request) throws Exception {
		if (upi == null) {
			throw new EzaishengUCException("系统异常");
		}
		log.info("修改手机号码前验证验证码：mobile=" + mobile + "  &code=" + code);
		Result result = Result.success();
		result = checkUpMoCodeParam(mobile, code);
		if (result.getSuccess()) {
			@SuppressWarnings("unchecked")
			RedisResult<String> ftcode = (RedisResult<String>) RedisUtils.get(mobile + "UPMOCODE", String.class);
			if (ftcode != null && ftcode.getResult() != null && ftcode.getCode() == RedisConstants.SUCCESS) {
				String temCode = ftcode.getResult();
				if (temCode.trim().equals(code.trim())) {
					result.setSuccess(true);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					result.setMsg("操作成功");
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("验证码错误");
				}
				// 删除缓存中验证码
				RedisUtils.del(mobile + "UPMOCODE");
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请重新获取验证码");
			}
		} else {
			return result;
		}
		return result;
	}

	Result checkUpMoCodeParam(String mobile, String code) {
		Result result = Result.success();
		if (StringUtils.isEmpty(mobile) || !Tools.paramValidate(mobile, 1)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("手机号码格式错误");
		}
		if (StringUtils.isEmpty(code)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("验证码不能为空");
		}
		return result;
	}

	/**
	 * 检查修改密码前的验证码(忘记密码模块修改密码)
	 */
	@Override
	public Result checkSCCode(String mobile, String code) throws Exception {
		log.info("忘记密码模块修改密码：mobile=" + mobile + "  &code=" + code);
		Result result = Result.failure();
		result = checkFtCodeParam(mobile, code);
		if (result.getSuccess()) {
			@SuppressWarnings("unchecked")
			RedisResult<String> ftcode = (RedisResult<String>) RedisUtils.get(mobile + "JYBSC", String.class);
			if (ftcode != null && ftcode.getResult() != null && ftcode.getCode() == RedisConstants.SUCCESS) {
				String temCode = ftcode.getResult();
				if (temCode.trim().equals(code.trim())) {
					// 正确 进入下一步
					result.setMsg("正确 进入下一步");
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("验证码错误");
				}
				// 删除缓存中验证码
				RedisUtils.del(mobile + "JYBSC");
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("验证码错误");
			}
		} else {
			return result;
		}
		return result;
	}

	@Override
	public boolean userLogot(ezs_user upi, String cookieuserkey) throws Exception {
		if (upi != null) {
			RedisUtils.del(cookieuserkey);
		}
		return true;

	}

	@Override
	public String getUserMessage(String username) {
		return ezs_userMapper.getUserMessage(username);
	}

	@Override
	public Map<String, Object> checkCompany(HttpServletRequest request, String company) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result sharCodeTodo(String sharcode, String phone) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result userAddInfo(Result result, HttpServletRequest request, String userRole, String companyName,
			String address, String area_id, String mianIndustry_id, String companyType_id, String trueName, long sex_id,
			String tel, String email,ezs_user user ) {

		// 校验
		result = checkAdduserinfo(result, request, userRole, companyName, address, area_id, mianIndustry_id,
				companyType_id, trueName, sex_id, tel, email);
		if (!result.getSuccess()) {
			return result;
		}

		

		if (user != null) {
			// 商铺信息
			ezs_store story = new ezs_store();
			story.setAddress(address);
			story.setCompanyName(companyName);
			story.setAddTime(new Date());
			story.setUserType(userRole);
			story.setArea_id(Long.valueOf(area_id));
			story.setDeleteStatus(false);
			story.setAssets(0.0);
			story.setCovered(0.0);
			story.setDevice_num(0);
			story.setEmployee_num(0);
			story.setFixed_assets(0.0);
			story.setObtainYear(0);
			story.setRent(false);
			story.setStatus(0);
			story.setyTurnover(0.0);
			story.setAdmin_status(0);

			// 用户详情
			ezs_userinfo userinfo = new ezs_userinfo();
			userinfo.setAddTime(new Date());
			userinfo.setDeleteStatus(false);
			userinfo.setEmail(email);
			userinfo.setPhone(user.getName());
			userinfo.setSex_id(sex_id);
			userinfo.setStatus(0);
			userinfo.setUpdateStatus(0);

			String[] Industrys = mianIndustry_id.split(",");
			String[] cmtypes = companyType_id.split(",");

			int aa = 0;
			try {
				long storyid = ezs_storeMapper.insert(story);
				storyid = story.getId();
				// 主营行业部分
				Industry(Industrys, storyid);
				;
				// 经营类型部分
				companyType(cmtypes, storyid);

				long userinfoid = ezs_userinfoMapper.insert(userinfo);
				user.setUserInfo_id(userinfoid);
				user.setStore_id(storyid);
				user.setUserRole(userRole);
				user.setTrueName(trueName);
				user.setStore_id(storyid);
				aa = ezs_userMapper.insert(user);
				ezs_user_role role = new ezs_user_role();

				role.setRole_id((long) 4);
				role.setUser_id(user.getId());
				ezs_user_roleMapper.insert(role);
			} catch (Exception e) {
				log.info("注册：保存用户注册信息错误" + e.toString());
				result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_FAIL);
				result.setSuccess(false);
				result.setMsg("系统错误");
			}

			if (aa > 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_SUCCESS);
				result.setSuccess(true);
				result.setMsg("注册成功");
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_FAIL);
				result.setSuccess(false);
				result.setMsg("注册失败");
			}
		} else {
			result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_FAIL);
			result.setSuccess(false);
			result.setMsg("请重新注册");
		}

		return result;
	}

	private void Industry(String[] Industrys, long store) {
		ezs_industry_dictMapper.delIndustryDictByStoreId(store);
		for (String long1 : Industrys) {
			ezs_industry_dictMapper.insert(new ezs_industry_dict(store, Long.valueOf(long1)));
		}
	}

	private void companyType(String[] cmtypes, long store) {
		ezs_companyType_dictMapper.delCompanyTypeByStoreId(store);
		for (String long1 : cmtypes) {
			ezs_companyType_dictMapper.insert(new ezs_companyType_dict(store, Long.valueOf(long1)));
		}

	}

	private Result checkAdduserinfo(Result result, HttpServletRequest request, String userRole, String companyName,
			String address, String area_id, String mianIndustry_id, String companyType_id, String trueName, long sex_id,
			String tel, String email) {

		List<ezs_store> list = ezs_storeMapper.getstoreInfoByName(companyName);

		if (list != null && list.size() > 0) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("公司名称已经存在");
			return result;
		}

		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("公司名称不能为空");
			return result;
		}
		if (Tools.isEmpty(address)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("详细地址不能为空");
			return result;
		}
		if (Tools.isEmpty(area_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择经营地址");
			return result;
		}
		if (Tools.isEmpty(mianIndustry_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择主营行业");
			return result;
		}
		if (Tools.isEmpty(companyType_id)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择公司类型");
			return result;
		}
		if (Tools.isEmpty(trueName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("姓名不能为空");
			return result;
		}

		if (!Tools.isEmpty(tel)) {
			if (!Tools.isMobileAndPhone(tel)) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("固定电话格式不正确");
				return result;
			}
		}

		if (Tools.isEmpty(email)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("邮箱不能为空");
			return result;
		}

		if (!Tools.paramValidate(email, 2)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("邮箱格式不正确");
			return result;
		}
		result.setSuccess(true);
		return result;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result upUserInfo(HttpServletRequest request, ezs_user ezsuser, String typeval, LinkUserVo linkvo) {
		Result result = Result.success();
		if (Tools.isEmpty(typeval)) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			return result;
		}
		ezs_userinfo upuser = new ezs_userinfo();
		upuser.setId(ezsuser.getEzs_userinfo().getId());

		ezs_user uupi = new ezs_user();
		uupi.setId(ezsuser.getEzs_userinfo().getId());
		switch (typeval) {
		case "truename":
			if (Tools.isEmpty(linkvo.getTruename()) && !Tools.paramValidate(linkvo.getTruename(), 3)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("真实姓名格式不正确！");
			} else {
				uupi.setTrueName(linkvo.getTruename());
				ezs_userMapper.updateByPrimaryKeySelective(uupi);
			}

			break;
		case "username":
			if(uupi.getEzs_userinfo().getUpdateStatus()==1){
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("您已经修改过一次,只有一次修改机会");
				return  result;
			}
			
			
			if (Tools.isEmpty(linkvo.getTruename()) && !Tools.paramValidate(linkvo.getTruename(), 3)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("登陆名称不正确！");
			} else {
				ezs_userinfo upuser1 = new ezs_userinfo();
				upuser.setId(ezsuser.getEzs_userinfo().getId());
				upuser.setUpdateStatus(1);
				ezs_userinfoMapper.updateByPrimaryKeySelective(upuser1);
				
				uupi.setName(linkvo.getUsername());
				ezs_userMapper.updateByPrimaryKeySelective(uupi);
			}

			break;	
		case "sex":
			upuser.setSex_id(linkvo.getSex());
			ezs_userinfoMapper.updateByPrimaryKeySelective(upuser);
			break;
		case "position":
			upuser.setPosition_id(linkvo.getPosition());
			ezs_position ezsposition = ezs_positionMapper.selectByStoryid(ezsuser.getStore_id());
			if (null == ezsposition) {
				ezsposition = new ezs_position();
				ezsposition.setAddTime(new Date());
				ezsposition.setDeleteStatus(true);
				ezsposition.setName(linkvo.getPositionval());
				ezsposition.setStore_id(ezsuser.getStore_id());
				ezs_positionMapper.insertSelective(ezsposition);
			} else {
				ezsposition.setName(linkvo.getPositionval());
				ezs_positionMapper.updateByPrimaryKeySelective(ezsposition);
			}
			break;
		case "tel":
			if (Tools.isEmpty(linkvo.getTel()) && !Tools.paramValidate(linkvo.getTel(), 1)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("电话号格式不正确！");
			} else {
				upuser.setTel(linkvo.getTel());
				ezs_userinfoMapper.updateByPrimaryKeySelective(upuser);
			}
			break;
		case "email":
			if (Tools.isEmpty(linkvo.getEmail()) && !Tools.paramValidate(linkvo.getEmail(), 2)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("邮箱格式不正确！");
			} else {
				upuser.setTel(linkvo.getEmail());
				ezs_userinfoMapper.updateByPrimaryKeySelective(upuser);
			}
			break;
		case "qq":
			if (Tools.isEmpty(linkvo.getQq()) && !Tools.paramValidate(linkvo.getQq(), 6)) {
				result.setSuccess(false);
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("qq号格式不正确！");
			} else {
				upuser.setTel(linkvo.getQq());
				ezs_userinfoMapper.updateByPrimaryKeySelective(upuser);
			}
			break;
		default:
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			break;
		}
		try {
			ezsuser = ezs_userMapper.getUserInfoByUserNameFromBack(ezsuser.getName()).get(0);
			RedisUserSession.updateUserInfo(ezsuser.getUserkey(), ezsuser,
					Long.parseLong(redisuserkeyexpir));
		} catch (NumberFormatException e) {
			log.info("h5设置个人资料" + ezsuser.getName() + "错误" + e.toString());
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
		}

		return result;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Result upStoreInfo(HttpServletRequest request, ezs_store store, ezs_user upi) {
		Result result = upcomvali(request, store);
		if (result.getSuccess()) {
			String address = request.getParameter("address");// 经营地址
			String assets = request.getParameter("assets");// 总资产
			String companyName = request.getParameter("companyName");// 企业名称
			String covered = request.getParameter("covered");// 占地面积
			String device_num = request.getParameter("device_num");// 设备数量
			String employee_num = request.getParameter("employee_num");// 员工数量
			String fixed_assets = request.getParameter("fixed_assets");// 固定资产
			String obtainYear = request.getParameter("obtainYear");// 实际控制人从业年限
			String rent = request.getParameter("rent");// 租用
			String yTurnover = request.getParameter("yTurnover");//// 年营业额

			String area_id = request.getParameter("area_id");// 经营地址区县

			String companyTypes = request.getParameter("companyType_id");// 公司类型
			String mianIndustrys = request.getParameter("mianIndustry_id");// 主营行业

			String[] Industrys = mianIndustrys.split(",");
			String[] cmtypes = companyTypes.split(",");

			try {
				// 主营行业部分
				Industry(Industrys, upi.getStore_id());
				;
				// 经营类型部分
				companyType(cmtypes, upi.getStore_id());

				store.setArea_id(Long.valueOf(area_id));
				store.setId(upi.getStore_id());
				store.setAddress(address);
				store.setCompanyName(companyName);
				store.setyTurnover(Double.valueOf(yTurnover));
				store.setCovered(Double.valueOf(covered));
				store.setDevice_num(Integer.valueOf(device_num));
				store.setEmployee_num(Integer.valueOf(employee_num));
				store.setAssets(Double.valueOf(assets));
				store.setFixed_assets(Double.valueOf(fixed_assets));
				store.setObtainYear(Integer.valueOf(obtainYear));
				store.setRent(Boolean.valueOf(rent));

				ezs_storeMapper.updateByPrimaryKeySelective(store);
				upi = ezs_userMapper.getUserInfoByUserNameFromBack(upi.getName()).get(0);
				RedisUserSession.updateUserInfo(RedisUserSession.getUserKey(cookieuserkey, request), upi,
						Long.parseLong(redisuserkeyexpir));
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("保存成功");
			} catch (Exception e) {
				log.info("h5设置公司资料" + upi.getName() + "错误" + e.toString());
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("参数错误");
			}
		}

		return result;
	}

	static Result upcomvali(HttpServletRequest request, ezs_store store) {
		Result result = Result.success();
		String address = request.getParameter("address");// 经营地址
		String assets = request.getParameter("assets");// 总资产
		String companyName = request.getParameter("companyName");// 企业名称
		String covered = request.getParameter("covered");// 占地面积
		String device_num = request.getParameter("device_num");// 设备数量
		String employee_num = request.getParameter("employee_num");// 员工数量
		String fixed_assets = request.getParameter("fixed_assets");// 固定资产
		String obtainYear = request.getParameter("obtainYear");// 实际控制人从业年限
		String rent = request.getParameter("rent");// 租用
		String yTurnover = request.getParameter("yTurnover");//// 年营业额

		String companyTypes = request.getParameter("companyType_id");// 公司类型
		String mianIndustrys = request.getParameter("mianIndustry_id");// 主营行业

		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入公司名称");
			return result;
		}
		if (Tools.isEmpty(address)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入详细地址");
			return result;
		}
		if (Tools.isEmpty(mianIndustrys)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择主营行业");
			return result;
		}
		if (Tools.isEmpty(companyTypes)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择公司类型");
			return result;
		}
		if (Tools.isEmpty(yTurnover)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入年营业额");
			return result;

		}
		if (!Tools.isNum(yTurnover)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效年营业额");
			return result;
		}
		if (Tools.isEmpty(covered)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入场地面积");
			return result;
		}
		if (!Tools.isNum(covered)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效场地面积");
			return result;
		}

		if (Tools.isEmpty(device_num)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入设备数量");
			return result;
		}
		if (!Tools.isNum(device_num)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效设备数量");
			return result;
		}
		if (Tools.isEmpty(employee_num)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入员工数量");
			return result;

		}
		if (!Tools.isNum(employee_num)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效员工数量");
			return result;
		}
		if (Tools.isEmpty(assets)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入总资产");
			return result;

		}
		if (!Tools.isNum(assets)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效总资产");
			return result;
		}
		if (Tools.isEmpty(fixed_assets)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入固定资产");
			return result;
		}
		if (!Tools.isNum(fixed_assets)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效固定资产");
			return result;
		}
		if (Tools.isEmpty(obtainYear)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入实际控制人从业年限");
			return result;
		}
		if (!Tools.isNum(obtainYear)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请输入有效实际控制人从业年限");
			return result;
		}
		if (Tools.isEmpty(rent)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("请选择是否租用");
			return result;
		}
		return result;
	}

	@Override
	public Result sendToUpMoPhoneCheck(String mobile, String code, ezs_user upi, HttpServletRequest request)
			throws EzaishengUCException {
		if (upi == null) {
			throw new EzaishengUCException("系统异常");
		}
		log.info("修改手机号码前验证验证码：mobile=" + mobile + "  &code=" + code);
		Result result = Result.success();
		result = checkUpMoCodeParam(mobile, code);
		if (result.getSuccess()) {
			@SuppressWarnings("unchecked")
			RedisResult<String> ftcode = (RedisResult<String>) RedisUtils.get(mobile + "TOUPMOCODE", String.class);
			if (ftcode != null && ftcode.getResult() != null && ftcode.getCode() == RedisConstants.SUCCESS) {
				String temCode = ftcode.getResult();
				if (temCode.trim().equals(code.trim())) {
					// 正确 进入下一步
//					ezs_user uupi = new ezs_user();
//					uupi.setName(mobile);
//					uupi.setId(upi.getId());
					ezs_userinfo info = new ezs_userinfo();
					info.setPhone(mobile);
					info.setPhoneStatus(1);
					info.setId(upi.getEzs_userinfo().getId());
					int temaa = ezs_userinfoMapper.updateByPrimaryKeySelective(info);
				
//					int temaa1 = ezs_userMapper.updateByPrimaryKeySelective(uupi);
					if (temaa == 1) {
						// 更新缓存
						//upi.setName(mobile);
						upi.getEzs_userinfo().setPhone(mobile);
						RedisUserSession.updateUserInfo(RedisUserSession.getUserKey(cookieuserkey, request), upi,
								Long.parseLong(redisuserkeyexpir));
						result.setSuccess(true);
						result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						result.setMsg("操作成功");
					} else {
						result.setSuccess(false);
						result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						result.setMsg("系统错误");
					}

				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("验证码错误");
				}
				// 删除缓存中验证码
				RedisUtils.del(mobile + "TOUPMOCODE");
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("请重新获取验证码");
			}
		} else {
			return result;
		}
		return result;
	}

	@Override
	public Result sendToUpMoCode(String phone, String code) throws Exception {
		Result result = Result.failure();
		if (StringUtils.isEmpty(phone) || !Tools.paramValidate(phone, 1)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("手机号码格式错误");
		} else {
			int istrue = ezs_userMapper.checkMobile(phone);
			if (istrue == 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PHONE_TYPE_REGISTERED);
				result.setSuccess(false);
				result.setMsg("手机号码已存在,请输入其他号码");
				return result;
			}
			// 先判断发送的次数 和 时间间隔
			Long totalcodetimes = Long.parseLong(mobilesendtimes);
			// 这里需要改
			RedisResult<Integer> rrtin = (RedisResult<Integer>) RedisUtils.get(phone + "TOUPMOCODE" + "times",
					Integer.class);
			Integer codetimes = 0;
			// 先判断发送的次数
			if (rrtin.getCode() == RedisConstants.SUCCESS) {
				codetimes = rrtin.getResult();
			}
			if (codetimes < totalcodetimes) {
				// 判断时间间隔
				Long expir = Long.parseLong(mobilesendcodeexpir);
				Long codeinterval = Long.parseLong(mobileinterval);
				RedisResult<Long> reexpirresult = (RedisResult<Long>) RedisUtils.getExpir(phone + "TOUPMOCODE");
				Long reexpir = 0l;
				if (reexpirresult.getCode() == RedisConstants.SUCCESS) {
					reexpir = reexpirresult.getResult();
				}
				if (expir - reexpir > codeinterval) {
					// 间隔大于60 可以发送短信
					// 短信内容
					String content = "您的短信验证码:" + code.toString() + ",请勿告诉他人,有效时间为" + mobilesendcodeexpir + "分钟!";
					log.info("短信验证码,发送内容:" + content);
					try {
						SendMobileMessage.sendMsg(phone, content);
						result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						result.setSuccess(true);
						result.setObj(new HashMap<>().put("mobile", phone));
						result.setMsg("验证码发送成功");
						RedisUtils.set(phone + "TOUPMOCODE", code, Long.parseLong(mobilesendcodeexpir));
						// 这里需要改
						RedisUtils.set(phone + "TOUPMOCODE" + "times", ++codetimes, DateUtils.getTimeValue());
					} catch (Exception e) {
						log.error("短信验证码功能失败");
						log.error(e.toString());
						result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						result.setSuccess(false);
						result.setMsg("服务器异常");
					}
				} else {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setSuccess(false);
					result.setMsg("请等待" + mobileinterval + "s后再次点击");
				}
			} else {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("验证码已发送多次，请查收短信");
			}

		}
		return result;
	}

}
