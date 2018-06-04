package com.sanbang.cata.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 二级三级列表
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/childList")
	@ResponseBody
	public Result getChildList(HttpServletRequest request,Long id){
		Result result=Result.failure();
		List secondList = cataService.getSecondList(id);
		List thirdList = new ArrayList();
		Map map2 = new HashMap();	//用map拼前端的json
		List transList = new ArrayList();
		for(int n=0; n < secondList.size(); n++){
			Map map = new HashMap();
			GoodsClass gc = (GoodsClass)secondList.get(n);
			thirdList = cataService.getThirdList(gc.getSecondId());
			map.put("third", thirdList);	//为符合前端要求的层级结构
			map.put("secondName", gc.getSecondName());
			transList.add(map);
			map2.put("second",transList);
		}
		if(null!=map2){
			result.setMsg("查询成功");
			result.setSuccess(true);
			result.setObj(map2);
		}
		return result;
	}
	
	
}
