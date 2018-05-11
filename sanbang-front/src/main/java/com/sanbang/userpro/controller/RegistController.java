package com.sanbang.userpro.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.IpUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.MessageDictionary;

@Controller
@RequestMapping("/userRegist")
public class RegistController {

	
	private  static final String view="/memberuser/regist/";
	
	@Autowired
	private UserProService userProService;
	
	//注册验证码标识
	@Value("${consparam.mobile.recode}")
	private String mobilerecode;
	
	//验证码有效时间
	@Value("${consparam.mobile.sendcodeexpir}")
	private String mobilesendcodeexpir;
	
	//验证码发送的间隔
	@Value("${consparam.mobile.interval}")
	private String mobileinterval;
	
	//#短信验证码发送的次数
	@Value("${consparam.mobile.sendtimes}")
	private String mobilesendtimes;
	
	/**
	 * 检查手机号码是否存在
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkMobile")
	@ResponseBody
	public Object checkMobile(@RequestParam(value="mobile",required=false)String mobile) throws Exception{
		Result  res=userProService.checkMobile(mobile);
		return res;
	}
	
	
	/**
	 * 用户注册发送修改手机号码的验证码
	 * @param phone
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/sendRgtCode")
	@ResponseBody
	public Object sendRgtCode(@RequestParam(value="mobile",required=false) String mobile, HttpServletRequest request) throws Exception {  
		Result result = Result.failure();
		StringBuilder code = new StringBuilder();
		Random random = new Random();
		// 6位验证码
		for (int i = 0; i < 6; i++) {
			code.append(String.valueOf(random.nextInt(10)));
		}
		// TODO
		// 分享码业务处理
		String sharcode = request.getParameter("sharcode");
		userProService.sharCodeTodo(sharcode, mobile);
		
		result = userProService.sendCode(mobile, code.toString(), mobilerecode, mobilesendcodeexpir, mobileinterval,
				mobilesendtimes, null, MessageDictionary.registCode(code));

		return result;
	}
	
	
	
	/**
	 * 用户注册发送修改手机号码的验证码
	 * @param phone
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/sendUpMoCode")
	@ResponseBody
	public Map<String,Object> sendUpMoCode(@RequestParam(value="mobile",required=false) String mobile, HttpServletRequest request) throws Exception {  
		
		StringBuilder code = new StringBuilder();  
		Random random = new Random();  
		// 6位验证码  
		for (int i = 0; i < 6; i++) {  
			code.append(String.valueOf(random.nextInt(10)));  
		}  
		Map<String,Object> map=userProService.sendUpMoCode(mobile,code.toString());
		return map;
	}  
	
	
	/**
	 * 注册新用户
	 * @param username
	 * @param passwd
	 * @param mobile
	 * @param code
	 * @param session
	 * @param //其他注册方式  flag 为4qq注册 5微信注册
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/userAdd")
	@ResponseBody
	public Result userAdd(
			@RequestParam(value="passwd",required=false) String passwd,
			@RequestParam(value="passwdA",required=false) String passwdA,
			@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="code",required=false) String code,
			HttpServletRequest request,HttpSession session) throws Exception{
//		username="t000007";
//		passwd="14e1b600b1fd579f47433b88e8d85291";
//		mobile="13717706563";
		Result result=Result.failure();
		String myip=IpUtils.getIpAddr(request);
		result= userProService.userAdd(mobile, passwd,passwdA, mobile,code,myip,null,session,request);
		return result;
	}
	
}
