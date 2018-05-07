//package com.sanbang.controller;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.sanbang.userpro.service.impl.TemplPageService;
//
//
///**
// * @author zhangxiantao 2016-9-2
// */
//@Controller
//@RequestMapping("/templPage")
//public class TemplPageController {
//	
//	private static Logger log = Logger.getLogger(TemplPageService.class.getName());
//	
//	@Resource(name="templPageService")
//	private TemplPageService templPageService;
//	
//	@RequestMapping(value="/index")
//	@ResponseBody
//	public String index(String flag) throws Exception {
//		
//		log.info("接收到首页数据更新的信息,标识:"+flag);
//		
//		String result = templPageService.getIndexMode();
//		
//		return result;
//	}
//
//}
