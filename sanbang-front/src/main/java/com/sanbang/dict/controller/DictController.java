package com.sanbang.dict.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.area.service.AreaService;
import com.sanbang.dict.service.DictService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;


@Controller
@RequestMapping("/dict")
public class DictController {

	private Logger log=Logger.getLogger(DictController.class);
	
	@Autowired
	private DictService dictService;
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 注册联系人资料初始化 资料
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/registInit")
	public Object getRegistDictDate(){
		Result result=Result.success();
		Map<String, Object> map=new HashMap<>();
		try {
			//主营行业
			map.put("industry", dictService.getDictByParentId(DictionaryCate.EZS_INDUSTRY));
			//公司类型
			map.put("comtype", dictService.getDictByParentId(DictionaryCate.EZS_COMPANYTYPE));
			//性别
			map.put("sex", dictService.getDictByParentId(DictionaryCate.EZS_SEX));
			
			map.put("area", areaService.getAreaParentList());
			
			result.setObj(map);
		} catch (Exception e) {
			log.info("字典(注册联系人资料初始化 资料)错误"+e.toString());
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_REGIST_FAIL);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		
		return result;
	}
	
}
