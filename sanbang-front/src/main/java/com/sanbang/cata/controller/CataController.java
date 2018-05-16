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
	
	@RequestMapping("/onelevelList")
	@ResponseBody
	public Result getOnelevelList(HttpServletRequest request){
		List list = cataService.getOnelevelList();
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		return   result;
	}
	
	//查询二级分类列表
	@RequestMapping("/twolevelList")
	@ResponseBody
	public Result getTwolevelList(HttpServletRequest request,long parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	//查询三级分类列表
	@RequestMapping("/threelevelList")
	@ResponseBody
	public Result getThreelevelList(HttpServletRequest request,long parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	
	
	//自营、地区筛选、品类筛选
	@RequestMapping("/areaAndType")
	@ResponseBody
	public Result listByAreaAndType(HttpServletRequest request,String area,String type){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	
	//其他筛选（颜色、形态、来源、用途、重要参数、燃烧等级、是否环保）
	@RequestMapping("/others")
	@ResponseBody
	public Result listByOthers(HttpServletRequest request,String... terms){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
}
