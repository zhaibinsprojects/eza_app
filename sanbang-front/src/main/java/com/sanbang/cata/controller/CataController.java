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
		System.out.println("我被访问了");
		//查询一级分类列表
		List list = cataService.getListForClass();
		System.out.println(list);
		
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		return   result;
	}
	
	//查询二级分类列表
	public Result getCataList2(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	//查询三级分类列表
	public Result getCataList3(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	
	
	//自营、地区筛选、品类筛选
	public Result listByAreaAndType(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	
	//其他筛选（颜色、形态、来源、用途、重要参数、燃烧等级、是否环保）
	public Result listByOthers(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	
	
}
