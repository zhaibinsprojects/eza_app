package com.sanbang.cata.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/cata")
public class CataController {

	private static final String  view="/cata/";
	
	@RequestMapping("/init")
	public String cataInit(HttpServletRequest request){
		
		
		return view+"catainit";
	}
	
}
