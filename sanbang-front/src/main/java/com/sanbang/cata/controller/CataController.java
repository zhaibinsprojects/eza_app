package com.sanbang.cata.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.cata.service.CataService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;


@Controller
@RequestMapping("/cata")
public class CataController {
	
	@Autowired
	private CataService cataService;

	private static final String  view="/cata/";
	
	@RequestMapping("/init")
	@ResponseBody
	public Result getCataList(HttpServletRequest request){
		//查询一级分类列表
		List list = cataService.getListForClass();
		System.out.println(list);
		
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		return   result;
	}
	
}
