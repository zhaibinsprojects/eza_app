package com.sanbang.hangq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.utils.Result;

@Controller
@RequestMapping("/app/hangq/")
public class HomeHangqIndexController {
	
	/**
	 * 价格行情首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getHangqHomeMess")
	@ResponseBody
	public Result getHangqHomeMess(HttpServletRequest request,HttpServletResponse response){
		Result rs = Result.success();
		
		return rs;
	}

}
