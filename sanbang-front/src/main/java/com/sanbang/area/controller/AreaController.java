package com.sanbang.area.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_area;
import com.sanbang.utils.JsonUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/area")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	/**
	 * 得到一级数据
	 * @return
	 */
	@RequestMapping(value="/getAreaParentList")
	@ResponseBody
	public Result getAreaParentList(){
		Result result=Result.failure();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(areaService.getAreaParentList());
		return result;
	}
	
	/**
	 * 得到子子级数据
	 * @param areaid
	 * @return
	 */
	@RequestMapping(value="/getAreaListByParId")
	@ResponseBody
	public Result getAreaListByParId(@RequestParam(required=true,value="areaid")long areaid){
		Result result=Result.failure();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		result.setSuccess(true);
		result.setObj(areaService.getAreaListByParId(areaid));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/areaToJson")
	public Object areaToJson(){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_area> listp=	areaService.getAreaParentList();
		 for (ezs_area ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("areaId", ezs_area.getId());
			map.put("areaName", ezs_area.getAreaName());
			map.put("cities", getarraytwo(ezs_area));
			list.add(map);
		}
		 System.err.println(JsonUtils.jsonOut(list));
		return JsonUtils.jsonOut(list); 
	}
	
	private  List<Map<String, Object>>   getarraytwo(ezs_area arae){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_area> listp=	areaService.getAreaListByParId(arae.getId());
		for (ezs_area ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("areaId", ezs_area.getId());
			map.put("areaName", ezs_area.getAreaName());
			map.put("counties", getarraythree(ezs_area));
			list.add(map);
		}
		
		
		return list;
	}
	
	
	private  List<Map<String, Object>>   getarraythree(ezs_area arae){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_area> listp1=	areaService.getAreaListByParId(arae.getId());
		for (ezs_area ezs_area : listp1) {
			Map<String, Object> map=new HashMap<>();
			map.put("areaName", ezs_area.getAreaName());
			map.put("areaId", ezs_area.getId());
			list.add(map);
		}
		return list;
	}
}
