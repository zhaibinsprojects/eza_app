package com.sanbang.area.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
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
}
