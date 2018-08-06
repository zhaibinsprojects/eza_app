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
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.cata.service.CataService;
import com.sanbang.dict.service.DictService;
import com.sanbang.utils.JsonUtils;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsClass;

@Controller
@RequestMapping("/area")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	@Autowired
	private DictService dictService;
	
	@Autowired
	CataService cataService; 
	
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
		result.setObj(areaid==0?areaService.getAreaParentList():areaService.getAreaListByParId(areaid));
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/areaToJson")
	public Object areaToJson(){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_area> listp=	areaService.getAreaParentList();
		 for (ezs_area ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			/*map.put("areaId", ezs_area.getId());
			map.put("areaName", ezs_area.getAreaName());
			map.put("cities", getarraytwo(ezs_area));*/
			map.put("value", ezs_area.getId());
			map.put("text", ezs_area.getAreaName());
			map.put("children", getarraytwo(ezs_area));
			list.add(map);
		}
		 System.err.println(JsonUtils.jsonOut(list));
		 System.err.println(JsonUtils.jsonOut(dictService.getDictByParentId(DictionaryCate.EZS_COLOR)));
		 System.err.println(JsonUtils.jsonOut(dictService.getDictByParentId(DictionaryCate.EZS_FORM)));
		 System.err.println(JsonUtils.jsonOut(dictService.getDictByParentId(DictionaryCate.EZS_SUPPLY)));
		return JsonUtils.jsonOut(list); 
	}
	
	private  List<Map<String, Object>>   getarraytwo(ezs_area arae){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_area> listp=	areaService.getAreaListByParId(arae.getId());
		if(listp.size()>0){
			Map<String, Object> map=new HashMap<>();
			map.put("value", "-1");
			map.put("text", "全部");
			list.add(map);
		}
		for (ezs_area ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_area.getId());
			map.put("text", ezs_area.getAreaName());
			map.put("children", getarraythree(ezs_area));
			list.add(map);
		}
		
		
		return list;
	}
	
	
	private  List<Map<String, Object>>   getarraythree(ezs_area arae){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_area> listp1=	areaService.getAreaListByParId(arae.getId());
		if(listp1.size()>0){
			Map<String, Object> map=new HashMap<>();
			map.put("value", "-1");
			map.put("text", "全部");
			list.add(map);
		}
		for (ezs_area ezs_area : listp1) {
			Map<String, Object> map=new HashMap<>();
			map.put("text", ezs_area.getAreaName());
			map.put("value", ezs_area.getId());
			list.add(map);
		}
		return list;
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/cataToJson")
	public Object cataToJson(){
		List<Map<String, Object>> list=new ArrayList<>();
		List<ezs_goods_class> listp = cataService.getFirstList();//cataService.getSecondList(parentsId);
		 for (ezs_goods_class ezs_goods_class : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_goods_class.getId());
			map.put("text", ezs_goods_class.getName());
			map.put("children", getcataarraytwo(ezs_goods_class));
			list.add(map);
		}
		 System.err.println(JsonUtils.jsonOut(list));
		return JsonUtils.jsonOut(list); 
	}
	
	private  List<Map<String, Object>>   getcataarraytwo(ezs_goods_class ezs_goods_class){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClass> listp=	cataService.getSecondList(ezs_goods_class.getId());
		if(listp.size()>0){
			Map<String, Object> map=new HashMap<>();
			map.put("value", "-1");
			map.put("text", "全部");
			list.add(map);
		}
		for (GoodsClass ezs_area : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_area.getSecondId());
			map.put("text", ezs_area.getSecondName());
			map.put("children", getcataarraythree(ezs_area));
			list.add(map);
		}
		
		
		return list;
	}
	
	
	private  List<Map<String, Object>>   getcataarraythree(GoodsClass GoodsClass){
		List<Map<String, Object>> list=new ArrayList<>();
		List<GoodsClass> listp1=	cataService.getSecondList(GoodsClass.getSecondId());
		if(listp1.size()>0){
			Map<String, Object> map=new HashMap<>();
			map.put("value", "-1");
			map.put("text", "全部");
			list.add(map);
		}
		for (GoodsClass ezs_area : listp1) {
			Map<String, Object> map=new HashMap<>();
			map.put("value", ezs_area.getSecondId());
			map.put("text", ezs_area.getSecondName());
			list.add(map);
		}
		return list;
	}
}
