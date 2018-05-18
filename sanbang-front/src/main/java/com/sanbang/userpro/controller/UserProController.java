package com.sanbang.userpro.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.IpUtils;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.MessageDictionary;




/**
 * 用户注册  登陆  忘记密码  相关请求
 * @authorLENOVO 
 * 2016年9月7日
 */
@Controller
@RequestMapping("/userPro")
public class UserProController {
	
	@Resource(name="userProService")
	private UserProService userProService;
	
	@Value("${consparam.cookie.userkey}")
	private String cookieuserkey;
	
	@Value("${consparam.ser.baseurl}")
	private String serbaseurl;
	
	@Value("${consparam.cookie.useridcard}")
	private String USERIDCARD;
	
	@Value("${consparam.mobile.sendtimes}")
	private String sendtimes;
	
	
	
	private static final String SHOPPINGCARTNUM = "spcnum";

	
	
	
	
	/**无密登录
	 * 用户密码登陆发送短信验证码
	 * @param mobile
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/sendCode")
	@ResponseBody
	public Result sendCode(@RequestParam(value="mobile",required=false) String mobile, 
			HttpServletRequest request) throws Exception {  
		
		StringBuilder code = new StringBuilder();  
        Random random = new Random();  
        // 6位验证码  
        for (int i = 0; i < 6; i++) {  
            code.append(String.valueOf(random.nextInt(10)));  
        }  
        String content = null;
        Result result = Result.failure();
      //无密登录
		content = MessageDictionary.loginCode(code.toString());
		result=userProService.sendCode(mobile,code.toString(),"MOBILELOGINFLAG", "1800","60",sendtimes,null,content);
        return result;
    }  


	
	/**
	 * 用户密码登录验证
	 * @param userName
	 * @param passwd
	 * @param httpSession
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/userLoginpsw")
	@ResponseBody
	public Result userLoginpsw(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "passwd", required = false) String passwd,
			@RequestParam(value = "code", required = false) String code,
			HttpSession httpSession, HttpServletRequest request,
<<<<<<< HEAD
			HttpServletResponse response,Integer flag) throws Exception {
		
=======
			HttpServletResponse response) throws Exception {
>>>>>>> 80cd2c6286476e5384c4fe5222d561946e24b141
		Result result=Result.failure();
		String userAgent = request.getHeader("User-Agent");
		String ip = IpUtils.getIpAddr(request);
		result = userProService.login(userName, passwd, code, userAgent, ip,
					request, response,null);
		return result;
	}
	
	/**
	 * 用户验证码登录
	 * @param userName
	 * @param passwd
	 * @param httpSession
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/userLoginPhone")
	@ResponseBody
	public Result userLogin(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "passwd", required = false) String passwd,
			@RequestParam(value = "code", required = false) String code,
			HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Result result=Result.failure();
		String userAgent = request.getHeader("User-Agent");
		String ip = IpUtils.getIpAddr(request);
		result = userProService.login(userName, passwd, code, userAgent, ip,
					request, response,1);
		return result;
	}
	
	/**
	 * 当前登录用户退出
	 * @param httpSession
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userLogot")
	@ResponseBody
	public Result userLogot(HttpServletRequest request) throws Exception {
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		userProService.userLogot(upi,RedisUserSession.getUserKey(cookieuserkey, request));
		result.setSuccess(true);
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("退出成功");
		return result;
	}

	
	
	
	/**
	 * 用户忘记密码发送短信验证码  (修改密码)
	 * @param phone
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/sendFtCode")
	@ResponseBody
	public Result sendFtCode(@RequestParam(value="mobile",required=false) String mobile, HttpServletRequest request) throws Exception {
		Result result=Result.failure();
		
		//检查手机号
		/*result=userProService.checkUserName(mobile);
		if(!result.getSuccess()){
			return result;
		}*/
		//发送忘记密码验证码
		StringBuilder code = new StringBuilder();  
		Random random = new Random();  
		// 6位验证码  
		for (int i = 0; i < 6; i++) {  
			code.append(String.valueOf(random.nextInt(10)));  
		}
		result=userProService.sendFtCode(mobile,code.toString());
		return result;
	}
	
	/**
	 * 用户忘记密码 修改密码前验证短信验证码
	 * @param mobile
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkFtCode")
	@ResponseBody
	public Object checkFtCode(@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="code",required=false) String code,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result=Result.failure();
		result=userProService.checkFtCode(mobile, code,request,response);
		return result;
	}
	
	/**
	 * 修改密码
	 * @param passwd
	 * @param passwdA
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/modifyPasswd")
	@ResponseBody
	public Result chgPwd(@RequestParam(value="passwd",required=false)String passwd,@RequestParam(value="passwdA",required=false) String passwdA,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result=Result.failure();	
		result=userProService.chgPasswd(passwd, passwdA,request);
		return result;
	}
	
	/**
	 * 检查用户名是否存在
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkUserName")
	@ResponseBody
	public Result checkUserName(@RequestParam(value="userName",required=false)String userName) throws Exception{
		Result result=Result.failure();	
		result=userProService.checkUserName(userName);
		return result;
	}
	
	/**
	 * 检查手机号码是否存在
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkMobile")
	@ResponseBody
	public Result checkMobile(@RequestParam(value="mobile",required=false)String mobile) throws Exception{
		Result result=userProService.checkMobile(mobile);
		return result;
	}
	
	
	/**
	 * 检查用户名是否登录
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/checkLoginStatus")
	@ResponseBody
	public Map<String,Object> checkLoginStatus(String userKey,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String,Object> result = new HashMap<>();
		
		result.put("status", false);
		
		RedisResult<ezs_user> tempCached=(RedisResult<ezs_user>) RedisUtils.get(userKey,ezs_user.class);
		if(tempCached != null && tempCached.getCode() == RedisConstants.SUCCESS){
			result.put("status", true);
			result.put("username", tempCached.getResult().getName());
		}
		int mallnum=0;
		String username="";
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);

		if(null!=upi){
			username=upi.getName();
		}else{
			Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
            if (null==cookies) {
            	
            } else {
                for(Cookie cookie : cookies){
                	if(cookie.getName().equalsIgnoreCase("SHOPPINGKEY")){
                		username=cookie.getValue().toString();
                	}
                }
            }
		}
		RedisResult<Integer> redismallnum = (RedisResult<Integer>) RedisUtils
								.get(SHOPPINGCARTNUM + username,Integer.class);
		if(null!=redismallnum.getResult()){
			mallnum=redismallnum.getResult();	
		}
		result.put("mallnum", mallnum);	
		return result;
	}
	
}
