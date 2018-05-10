package com.sanbang.userpro.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.User_Proinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.userpro.service.UserProService;
import com.sanbang.utils.IpUtils;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;




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
	
	@Value("${consparam.cookie.cookieuserkeyexpir}")
	private String cookieuserkeyexpir;
	
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;
	
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
	
	private static final String SHOPPINGCARTNUM = "spcnum";

	
	/**
	 * 跳转到login页 action
	 */
	@RequestMapping(value="/toLoginPage")
	public String toLoginPage(HttpSession httpSession,HttpServletRequest request,HttpServletResponse response){
		
		return "memberuser/login";
	}
	
	/**
	 * 跳转到phonelogin页 action
	 */
	@RequestMapping(value="/toPhoneLoginPage")
	public String toPhoneLoginPage(HttpSession httpSession,HttpServletRequest request,HttpServletResponse response){
		
		return "memberuser/phonelogin";
	}
	
	
	/**
	 * 跳转到注册页 action
	 */
	@RequestMapping(value="/toRegPage")
	public String toRegPage(HttpServletRequest request,HttpServletResponse response){
		
		return "memberuser/regpage";
	}
	
	@RequestMapping(value="/sendContractCode")
	@ResponseBody
	public Object sendContractCode(String phone){
		StringBuilder code = new StringBuilder();  
        Random random = new Random();  
        // 6位验证码  
        for (int i = 0; i < 6; i++) {  
            code.append(String.valueOf(random.nextInt(10)));  
        } 
        Map<String,Object> map=userProService.sendContractCode(phone, code.toString(), null);
	    return map;
	}
	/**
	 * 判断公司名称是否存在
	 * @param company
	 * @return
	 */
	@RequestMapping(value="/checkCompany")
	@ResponseBody
	public Object checkCompany(HttpServletRequest request,String company){
		Map<String,Object> map=new HashMap<>();
		if(StringUtils.isEmpty(company)){
			map.put("status", "success");
			map.put("message", "公司名称不存在");
		}else{
			map=userProService.checkCompany(request,company);
		}
		return map;
	}

	
	/**
	 * 跳转到忘记密码发送短信验证码页 action
	 */
	@RequestMapping(value="/toFtPasswdPage")
	public String toFtPasswdPage(HttpServletRequest request,HttpServletResponse response){
		
		return "memberuser/ftpasswd";
	}
	
	/**
	 * 跳转到忘记密码校验密码页 action
	 */
	@RequestMapping(value="/checkFtCode/modifyPasswd")
	public String toFtModifyPasswd(HttpServletRequest request,HttpServletResponse response){
		return "memberuser/ftmodifypasswd";
	}
	
	
	
	/**
	 * 用户注册发送短信验证码
	 * @param mobile
	 * @param request
	 * @param flag 为空 是注册 为 1是 无密登录
	 * @throws Exception
	 */
	@RequestMapping(value="/sendCode")
	@ResponseBody
	public Map<String,Object> sendCode(@RequestParam(value="mobile",required=false) String mobile, 
			HttpServletRequest request,Integer flag,String imgcode) throws Exception {  
		StringBuilder code = new StringBuilder();  
        Random random = new Random();  
        // 6位验证码  
        for (int i = 0; i < 6; i++) {  
            code.append(String.valueOf(random.nextInt(10)));  
        }  
        String content = null;
        Map<String,Object> map=new HashMap<>();
        if(flag==null){
        	//注册信息  需要验证图片验证码
        	valipic(map, request, imgcode);
        	if(map.size()!=0){
        		return map;
        	}
        	content = "【易再生网】您的短信验证码:"+code.toString()+",请勿告诉他人,有效时间为10分钟!";
        	map=userProService.sendCode(mobile,code.toString(),mobilerecode, mobilesendcodeexpir,mobileinterval,mobilesendtimes,flag,content);
        }else{
        	if(flag==1){
        		//无密登录
        		valipic(map, request, imgcode);
            	if(map.size()!=0){
            		return map;
            	}
        		content = "【易再生网】尊敬的用户您好，您本次的动态登录码为"+code.toString()+"。温馨提示：您的动态登录码30分钟内有效，请安全保管。";
        		map=userProService.sendCode(mobile,code.toString(),"MOBILELOGINFLAG", "1800","60","3",flag,content);
        	}
        }
        return map;
    }  
	
	
	/**
	 * 验证登陆验证码
	 * @throws Exception 
	 */
	@RequestMapping(value="/loginRandImgVali")
	@ResponseBody
	public Map<String,Object> loginRandImgVali(String code,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map=userProService.loginRandImgVali(code, request);
		return map;
	}
	
	
	
	
	
	
	/**
	 * 用户登录验证
	 * @param userName
	 * @param passwd
	 * @param httpSession
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/userLogin")
	@ResponseBody
	public Map<String, Object> userLogin(
			@RequestParam(value = "userName", required = false) String userName,
			@RequestParam(value = "passwd", required = false) String passwd,
			@RequestParam(value = "code", required = false) String code,
			HttpSession httpSession, HttpServletRequest request,
			HttpServletResponse response,Integer flag) throws Exception {
//		userName="zhangscott";
//		passwd="af8e33b74e734545435bfbf9668e0f93";
		Map<String, Object> map = null;
		String userAgent = request.getHeader("User-Agent");
		String ip = IpUtils.getIpAddr(request);
		map = userProService.login(userName, passwd, code, userAgent, ip,
					request, response,flag);
		return map;
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
	public String userLogot(HttpServletRequest request) throws Exception {
		User_Proinfo upi=RedisUserSession.getUserInfo(request);
//	    UserProInfo upi=new UserProInfo();
//	    upi.setUsername("t000003");
		Map<String, Object> map = null;
		userProService.userLogot(upi,RedisUserSession.getUserKey(cookieuserkey, request));
		return "redirect:"+serbaseurl;
	}
	/**
	 * 注册新用户
	 * @param username
	 * @param passwd
	 * @param mobile
	 * @param code
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/userAdd")
	@ResponseBody
	public Map<String, Object> userAdd(@RequestParam(value="userName",required=false) String username,
			@RequestParam(value="passwd",required=false) String passwd,
			@RequestParam(value="passwdA",required=false) String passwdA,
			@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="code",required=false) String code,
			@RequestParam(value="imgcode",required=false) String imgcode,
			HttpServletRequest request,Integer flag,HttpSession session) throws Exception{
//		username="t000007";
//		passwd="14e1b600b1fd579f47433b88e8d85291";
//		mobile="13717706563";
		String myip=IpUtils.getIpAddr(request);
		Map<String, Object> map = userProService.userAdd(username, passwd,passwdA, mobile,code,myip,flag,session,request,imgcode);
		return map;
	}
	
	
	
	public void valipic(Map<String,Object> map,HttpServletRequest request,String imgcode){
		//检验验证码
		String useridcard=RedisUserSession.getUserKey(USERIDCARD, request);
		
		if(StringUtils.isEmpty(useridcard)){
			map.put("imgcode", "请重新输入验证码");
		}else{
			RedisResult<String> vacode=null;
			vacode=(RedisResult<String>) RedisUtils.get(useridcard+"validatecode",String.class);
			if(vacode!=null&&vacode.getCode()==RedisConstants.SUCCESS){
				String valicode=vacode.getResult();
				if(valicode.equalsIgnoreCase(imgcode)){
				}else{
					map.put("imgcode", "验证码错误，请重新获取");
				}
			}else{
				map.put("imgcode", "验证码失效");
			}
		}
	}
	
	
	
	/**
	 * 修改手机号码  并 检验 验证码
	 * @param mobile
	 * @param code
	 * @return
	 */
	@RequestMapping(value="userInfo/checkUpMoCode")
	@ResponseBody
	public Map<String,Object> checkUpMoCode(@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="code",required=false) String code,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		User_Proinfo upi=RedisUserSession.getUserInfo(request);
//		   UserProInfo upi=new UserProInfo();
//		   upi.setUsername("tttttt");
		Map<String,Object> map=userProService.checkUpMoCode(mobile, code,upi,request);
		return map;
	}
	/**
	 * 用户忘记密码发送短信验证码  (修改密码)
	 * @param phone
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/sendFtCode")
	@ResponseBody
	public Map<String,Object> sendFtCode(@RequestParam(value="mobile",required=false) String mobile, HttpServletRequest request) throws Exception {
		StringBuilder code = new StringBuilder();  
		Random random = new Random();  
		// 6位验证码  
		for (int i = 0; i < 6; i++) {  
			code.append(String.valueOf(random.nextInt(10)));  
		}
//		HttpSession session = request.getSession();  
//		session.setAttribute("FTPHONE", phone);  
//		session.setAttribute("FTCODE", code.toString());  
//		session.setAttribute("FTSENDTIME", new Date().getTime());
		Map<String,Object> map=userProService.sendFtCode(mobile,code.toString());
		return map;
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
	public Map<String,Object> checkFtCode(@RequestParam(value="mobile",required=false) String mobile,
			@RequestParam(value="code",required=false) String code,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map=userProService.checkFtCode(mobile, code,request,response);
		return map;
	}
	
	/**
	 * 修改密码
	 * @param passwd
	 * @param passwdA
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkFtCode/modifyPasswd/chkPasswd")
	@ResponseBody
	public Map<String,Object> chgPwd(@RequestParam(value="passwd",required=false)String passwd,@RequestParam(value="passwdA",required=false) String passwdA,
			HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String,Object> map=userProService.chgPasswd(passwd, passwdA,request);
		return map;
	}
	
	/**
	 * 检查用户名是否存在
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkUserName")
	@ResponseBody
	public Map<String,Object> checkUserName(@RequestParam(value="userName",required=false)String userName) throws Exception{
			Map<String,Object> map=userProService.checkUserName(userName);
		return map;
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
		User_Proinfo upi=RedisUserSession.getUserInfo(request);

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
