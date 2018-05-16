package com.sanbang.index.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_user;
import com.sanbang.index.service.CustomerService;
import com.sanbang.utils.RedisUserSession;

@Controller
public class HomeCustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 获取用户信息（并判断是否已登录）
	 * @param request
	 * @param response
	 * @return （获取）
	 */
	@RequestMapping("/getUserMess")
	@ResponseBody
	public Object getUserMess(HttpServletRequest request,HttpServletResponse response){
		//获取缓存中已登录用户信息
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi!=null){
			//用户已登录
			//根据用户查询用户电话以及地址信息
		}else{
			//用户未登录
		}
		
		return "";
	}
	

}
