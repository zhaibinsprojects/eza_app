package com.sanbang.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.cata.service.CataService;
import com.sanbang.utils.Result;
import com.sanbang.vo.GoodsClass;

@Controller
@RequestMapping("/app/cata")
public class AppCataController {
	
	@Autowired
	private CataService cataService;
	
	/**
	 * 一级分类
	 * @param request
	 * @return
	 */
	@RequestMapping("/firstList")
	@ResponseBody
	public Result getFirstList(HttpServletRequest request){
		List list = cataService.getFirstList();
		Result result=Result.failure();
		result.setObj(list);
		return   result;
	}
	
	/**
	 * 二级三级列表
	 * @param request
	 * @param parentsId
	 * @return
	 */
	@RequestMapping("/childList")
	@ResponseBody
	public Result getChildList(HttpServletRequest request){
		Result result=Result.success();
		List<GoodsClass> list = cataService.getChildList();
		result.setObj(list);
		return result;
	}
	
}
