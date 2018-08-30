package com.sanbang.hangq.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app/pushDate")
public class PushDataHanqController {


	
	private static Logger log = Logger.getLogger(HomeHangqIndexController.class);
	

	
	private static String view="/hangqv2/";
	
	
	
	/**
	 * 订阅列表
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/dataShow")
	public String hangqgShow(Model model){
		
		return view+"pushData";
	}
	
}
