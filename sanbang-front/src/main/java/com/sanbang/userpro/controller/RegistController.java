package com.sanbang.userpro.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.userpro.service.UserProService;

@Controller
@RequestMapping("/userRegist")
public class RegistController {

	
	private  static final String view="/memberuser/regist/";
	
	@Autowired
	private UserProService userProService;
	
	/**
	 * 跳转到注册页面
	 * @return
	 */
	@RequestMapping("/toRegist")
	public String toRegist(){
		
		return view+"toregist";
	}
	
	
	/**
	 * 检查手机号码是否存在
	 * @param userName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkMobile")
	@ResponseBody
	public Map<String,Object> checkMobile(@RequestParam(value="mobile",required=false)String mobile) throws Exception{
		Map<String,Object> map=userProService.checkMobile(mobile);
		return map;
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
	
	
}
