package com.sanbang.goods.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;


@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	

	private static final String  view="/cata/";
	
	@RequestMapping("/init")
	@ResponseBody
	public Result getCataList(HttpServletRequest request){
		//查询一级分类列表
		List list = goodsService.getListForClass();
		System.out.println(list);
		
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		return   result;
	}
	
	//查询二级分类列表
	public Result getCataList2(HttpServletRequest request,String parentsId){
		//
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	//查询三级分类列表
	public Result getCataList3(HttpServletRequest request,String parentsId){
		
		
		
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	//自营商城下所有商品分类
	public Result listForSelf(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	//地区筛选
	public Result listByArea(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	//品类筛选
	public Result listByType(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	//其他筛选（颜色、形态、来源、用途、重要参数、燃烧等级、是否环保）
	public Result listByOthers(HttpServletRequest request,String parentsId){
		Result result=Result.success();
		
		
		
		
		return result;
	}
	
	
	
	
}
