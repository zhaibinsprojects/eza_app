package com.sanbang.buyer.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/buyer")
public class BuyerMenu {

	private static final String  view="/buyer/";
	
	@RequestMapping("/order")
	public String cataInit(HttpServletRequest request){
		
		
		return view+"buyerorder";
	}
	
}
