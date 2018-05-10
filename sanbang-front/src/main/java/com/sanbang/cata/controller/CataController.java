package com.sanbang.cata.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.utils.Page;
import com.sanbang.utils.Result;


@Controller
@RequestMapping("/cata")
public class CataController {

	private static final String  view="/cata/";
	
	@RequestMapping("/init")
	@ResponseBody
	public Result cataInit(HttpServletRequest request){
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		return   result;
	}
	
}
