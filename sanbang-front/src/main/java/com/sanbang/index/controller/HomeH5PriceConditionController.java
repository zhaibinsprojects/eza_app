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
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.index.service.AddressService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/app/home")
public class HomeH5PriceConditionController {
	
	private static String view="/hangq/";
	
	@Autowired
	private PriceConditionService priceConditionService;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private AddressService addressService;
	@Autowired
	private ezs_ezssubstanceMapper ezs_ezssubstanceMapper;
	@Autowired
	ezs_columnMapper columnMapper;
	
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
			@RequestParam(name="jiagecatid",defaultValue="12") int jiagecatid,
			@RequestParam(name="currentPage",defaultValue="1") int pageno,
			@RequestParam(name="kindId",defaultValue="1") String kindId,
			@RequestParam(name="areaId",defaultValue="4523541") String areaId,
			@RequestParam(name="countryType",defaultValue="1")String countryType,
			@RequestParam(name="colorId",defaultValue="")String colorId,
			@RequestParam(name="formId",defaultValue="")String formId,
			@RequestParam(name="source",defaultValue="")String source,
			@RequestParam(name="purpose",defaultValue="")String purpose,
			@RequestParam(name="burning",defaultValue="")String burning,
			@RequestParam(name="protection",defaultValue="")String protection,
			Model  model){
		//行情分析
		Map<String, Object>  jiage = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(jiagecatid), pageno);
		//研究报告 
		Map<String, Object>  baogao = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(yanjiucatid), pageno);
		
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
		zoushi = this.priceConditionService.getPriceTrendcy(tMp,pageno);
		baojia = this.priceConditionService.getPriceInTime(tMp,pageno);
		
		model.addAttribute("jiage", jiage);
		model.addAttribute("baogao", baogao);
		/*model.addAttribute("zoushi", zoushi);*/
		model.addAttribute("baojia", baojia);
		
		return view+"/hangqindex";
	}
	
	
	/**
	 * 查看文章详情
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/hangqShow")
	public String wenzhangShow(@RequestParam(name="id",required=true)long id,
			Model model){
		ezs_ezssubstance	show=ezs_ezssubstanceMapper.selectByPrimaryKey(id);
		ezs_column column=columnMapper.selectByPrimaryKey(show.getEc_id());
		ezs_ezssubstance	boytton=ezs_ezssubstanceMapper.getButtomOneSubstanceByid(id, column.getParentEzsColumn_id());
		ezs_ezssubstance	top=ezs_ezssubstanceMapper.getTopOneSubstanceByid(id, column.getParentEzsColumn_id());
		model.addAttribute("top", top);
		model.addAttribute("button", boytton);
		model.addAttribute("show", show);
		model.addAttribute("title", column.getName());
		
		return view+"infoshow";
	}
	
	
	/**
	 * 关于我们
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/ezsStatement")
	public String theStatement(@RequestParam(name="id",required=true,defaultValue="46")long id,
			Model model){
		ezs_ezssubstance	show=ezs_ezssubstanceMapper.selectByPrimaryKey(id);
		ezs_column column=columnMapper.selectByPrimaryKey(show.getEc_id());
		model.addAttribute("show", show);
		model.addAttribute("title", column.getName());
		return view+"ezsStatement";
	}
	
	
	/**
	 * 协议说明
	 * @param id
	 * @param catid
	 * @return
	 */
	@RequestMapping("/ezsintroduce")
	public String ezsintroduce(@RequestParam(name="id",required=true,defaultValue="44")long id,
			Model model){
		ezs_ezssubstance	show=ezs_ezssubstanceMapper.selectByPrimaryKey(id);
		ezs_column column=columnMapper.selectByPrimaryKey(show.getEc_id());
		model.addAttribute("show", show);
		model.addAttribute("title", column.getName());
		return view+"ezsintroduce";
	}
}
