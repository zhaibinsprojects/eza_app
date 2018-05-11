package com.sanbang.userpro.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.User_Proinfo;
import com.sanbang.utils.RedisUserSession;

@RequestMapping("/user")
@Controller
public class UserIndexController {

	private static final String view="/userMenu/";
	
	@RequestMapping("/index")
	public String UserIndexInit(HttpServletRequest request,Model model){
		
		
		return view+"userindex";
		
	}
	
}
