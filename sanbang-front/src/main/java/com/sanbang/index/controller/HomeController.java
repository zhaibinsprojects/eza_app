package com.sanbang.index.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/home")
public class HomeController {

	@RequestMapping("/index")
	public String toindex(HttpServletRequest request){
		
		return "index";
	}
}
