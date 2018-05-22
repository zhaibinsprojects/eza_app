package com.sanbang.cata.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_goods;
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
		List list = cataService.getTwolevelList(parentsId);
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		return result;
	}
	
	//查询三级分类列表
	@RequestMapping("/threelevelList")
	@ResponseBody
	public Result getThreelevelList(HttpServletRequest request,long parentsId){
		Result result=Result.success();
		List list = cataService.getThreelevelList(parentsId);
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(list);
		return result;
	}
	
	
}
