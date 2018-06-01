package com.sanbang.cata.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.cata.service.CataService;
import com.sanbang.utils.Result;
import com.sanbang.vo.GoodsClass;

@Controller
@RequestMapping("/cata")
public class CataController {
	
	@Autowired
	private CataService cataService;
	
	//一级
	@RequestMapping("/firstList")
	@ResponseBody
	public Result getFirstList(HttpServletRequest request){
		List<ezs_goods_class> list = cataService.getFirstList();
		Result result=Result.failure();
		result.setObj(list);
		return   result;
	}
	
	//二级三级
	@RequestMapping("/childList")
	@ResponseBody
	public Result getChildList(HttpServletRequest request){
		Result result=Result.success();
		List<GoodsClass> list = cataService.getChildList();
		result.setObj(list);
		return result;
	}
}
