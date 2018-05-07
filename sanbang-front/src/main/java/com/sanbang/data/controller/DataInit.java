package com.sanbang.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data")
public class DataInit {
    
	
	private static final String view="data/";
	
	@RequestMapping("/init")
	public String datainit(){
		
		return view+"init";
	}
    
}
