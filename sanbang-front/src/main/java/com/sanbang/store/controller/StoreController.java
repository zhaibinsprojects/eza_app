package com.sanbang.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_userMapper;

@Controller
@RequestMapping("/store")
public class StoreController {

	private static final String view="/shop";
	
	@Autowired
	private ezs_userMapper ezs_userMapper;
	
	@RequestMapping("/index")
	public String toStoreIndex(){
		ezs_user ezs_user=	ezs_userMapper.selectByPrimaryKey(1L);
		
		return view+"/storeindex";
	}
	
}  
