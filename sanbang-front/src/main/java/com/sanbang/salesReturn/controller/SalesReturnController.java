package com.sanbang.salesReturn.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.salesReturn.service.SalesReturnService;
import com.sanbang.utils.Result;

@Controller
@RequestMapping("/salesReturn")
public class SalesReturnController {
	
	private Logger log = Logger.getLogger(SalesReturnController.class);

	@Autowired
	private SalesReturnService salesReturnService;
	
	
//	@RequestMapping(value="/getGoodscardById")
//	@ResponseBody
//	public Object getGoodscardById(@RequestParam(required=true,value="goodscardId")int goodscardId){
//		Result result = Result.success();
//		
//	}
}
