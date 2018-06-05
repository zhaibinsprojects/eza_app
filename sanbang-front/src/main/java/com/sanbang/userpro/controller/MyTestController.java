package com.sanbang.userpro.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.bean.ezs_area;
import com.sanbang.userpro.service.TestService;
import com.sanbang.utils.JsonUtils;

@Controller
@RequestMapping("/adduser")
public class MyTestController {
	
	@Autowired
	private AreaService areaService;
	
	@Autowired
	private TestService test;
	
	@RequestMapping("/tuser")
	public void test(int num){
	 test.addUserInfo(num);
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
