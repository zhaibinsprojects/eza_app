package com.sanbang.index.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sanbang.bean.ezs_area;
import com.sanbang.index.service.AddressService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/home")
public class HomeH5PriceConditionController {
	
	private static String view="/hangq";
	
	@Autowired
	private PriceConditionService priceConditionService;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private AddressService addressService;
	
	private Logger log=Logger.getLogger(HomeH5PriceConditionController.class);
	
	
	
	/**
	 *  app/h5价格分析
	 * @param catid  大分类
	 * @param currentPage /pageno
	 * @param kindId 品类
	 * @param areaId  地区id
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/hangq")
	public String hangqin(@RequestParam(name="yanjiucatid",defaultValue="17") int yanjiucatid,
			@RequestParam(name="yanjiucatid",defaultValue="12") int jiagecatid,
			@RequestParam(name="currentPage",defaultValue="1") String currentPage,
			@RequestParam(name="kindId",defaultValue="32") String kindId,
			@RequestParam(name="areaId",defaultValue="4521984") String areaId,
			Model  model){
		//价格
		Map<String, Object>  baogao = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(jiagecatid), currentPage);
		//研究报告
		Map<String, Object>  jiage = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(yanjiucatid), currentPage);
		Map<String, Object> tMp = new HashMap<>();
		Map<String, Object> zoushi =new HashMap<>();
		Map<String, Object> baojia =new HashMap<>();
		//参数传递
		tMp.put("kindId", kindId);
		//获取相关地址ID
		List<String> areaIdsList = new ArrayList<>();
		Map<String, Object> areaIdsMap = null;
				areaIdsMap = this.addressService.getAllChildID(Long.valueOf(areaId));
				Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
				if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
					List<ezs_area> areaListTemp =new ArrayList<>();
					 areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
					for (ezs_area tarea : areaListTemp) {
						areaIdsList.add(tarea.getId().toString());
					}
					tMp.put("areaIds", areaIdsList);
				}
		zoushi = this.priceConditionService.getPriceTrendcy(tMp,Integer.valueOf(currentPage));
		baojia = this.priceConditionService.getPriceInTime(tMp);
		
		model.addAttribute("jiage", jiage);
		model.addAttribute("baogao", baogao);
		model.addAttribute("zoushi", zoushi);
		model.addAttribute("baojia", baojia);
		
		return view+"hangqindex";
	}
	
}
