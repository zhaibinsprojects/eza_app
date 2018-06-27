package com.sanbang.accountSafe.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.accountSafe.service.AccountSafeService;
import com.sanbang.app.controller.AppUserSetupLinkController;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.utils.javaMail.Mail;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.MessageDictionary;

@Controller
@RequestMapping("/accountSafe")
public class AccountSafeController {

	@Autowired
	private AccountSafeService  accountSafeService; 
	
	
	//设置密保手机验证码标识
	@Value("${consparam.mobile.ascode}")
	private String mobileascode;
	
	//验证码有效时间
	@Value("${consparam.mobile.sendcodeexpir}")
	private String mobilesendcodeexpir;
	
	//验证码发送的间隔
	@Value("${consparam.mobile.interval}")
	private String mobileinterval;
	
	//#短信验证码发送的次数
	@Value("${consparam.mobile.sendtimes}")
	private String mobilesendtimes;
	private Logger log = Logger.getLogger(AccountSafeController.class);
	/**
	 * 查看用户的账号安全状态，包括：实名认证，密保手机，邮箱认证 等情况
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkAccountStatus")
	@ResponseBody
	public Result checkAccountStatus(HttpServletRequest request){
		Result result = Result.success();
		
		try {
			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			result = accountSafeService.checkAccountStatus(request, upi);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("查询账户安全状态失败");
		}
		
		return result;
		
	}
	
	/**
	 * 获取用户的密保手机
	 * @param request
	 * @return
	 */
	@RequestMapping("/getSecuratePhone")
	@ResponseBody
	public Result getSecuratePhone(HttpServletRequest request){
		Result result = Result.success();
		
		try {
			ezs_user upi = RedisUserSession.getLoginUserInfo(request);
			if (upi == null) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
				result.setMsg("用户未登录");
				return result;
			}
			
			result = accountSafeService.getSecuratePhone(request, upi);
		} catch (Exception e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("密保手机设置失败");
		}
		
		return result;
	}
	
	@RequestMapping("/sendSecuratePhoneMsg")
	@ResponseBody
	public Result sendSecuratePhoneMsg(@RequestParam(value="mobile",required=true) String mobile,HttpServletRequest request){
		Result result = Result.success();
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		//6位验证码
		for(int i=0; i<6; i++){
			code.append(String.valueOf(random.nextInt(10)));
		}
		result = accountSafeService.sendCode(mobile,code.toString(),mobileascode,mobilesendcodeexpir,mobileinterval,mobilesendtimes,null,MessageDictionary.securatePhoneCode(code));
		return result;
		
	}
	
	
	@RequestMapping("/setOrUpdateSecuratePhone")
	@ResponseBody
	public Result setOrUpdateSecuratePhone(@RequestParam(value="mobile",required=true) String mobile,@RequestParam(value="code",required=true) String code,HttpServletRequest request){
		Result result = Result.success();
		
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		result = accountSafeService.setOrUpdateSecuratePhone(mobile,code,request,upi);
		return result;
		
	}
	
	
	/**
	 * 邮箱验证
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sendEmail")
	public  Result sendEmail(HttpServletRequest request){
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		log.info("给" + upi.getName() + "发送评标邮件>>>>>>>");
		
		if(Tools.isEmpty(upi.getEzs_userinfo().getEmail())){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("您还未填写邮箱！");
			return result;
		}
		String  secondHtml="请点击以下链接进行操作";
		Mail secondmail = new Mail(upi.getEzs_userinfo().getEmail(), "易再生网", secondHtml);
		secondmail.setSubject("邮箱验证:" + upi.getName() == null ? ""
				: upi.getName() + "关于修改邮箱-易再生网");
		log.info("评标邮件内容>>>>>>>" + secondHtml);
		result.setSuccess(true);
		result.setMsg("发送成功");
		return result;
	}
}
