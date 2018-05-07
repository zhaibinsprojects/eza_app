package com.sanbang.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/store")
public class StoreController {

	private static final String view="/shop";
	
	@RequestMapping("/index")
	public String toStoreIndex(){
		
		
		return view+"/storeindex";
	}
	
}  
