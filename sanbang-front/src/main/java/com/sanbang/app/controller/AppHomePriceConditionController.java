package com.sanbang.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.index.service.AddressService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.ExPage;
import com.sanbang.vo.PriceTrendIfo;

@Controller
@RequestMapping("/app/home")
public class AppHomePriceConditionController {
	@Autowired
	private PriceConditionService priceConditionService;
	@Autowired
	private IndustryInfoService industryInfoService;
	@Autowired
	private AddressService addressService;
	/*
	 * 需要先判断用户是否已登录
	 * 并是否已经订阅
	 */
	/**
	 * 价格行情推送-国内/实时报价
	 * @param request
	 * @param response
	 * @param countryType type 1-国内  3-实时
	 * @param kindId  产品类别
	 * @param colorId 颜色
	 * @param formId 形态
	 * @param source 来源
	 * @param purpose 用途
	 * @param burning 燃烧等级
	 * @param protection 是否环保
	 * @param areaId 定位地址
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPriceInTime")
	@ResponseBody
	public Object getPriceInTime(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name="countryType",defaultValue="1")String countryType,
			@RequestParam(name="kindId",defaultValue="")String kindId,
			@RequestParam(name="colorId",defaultValue="")String colorId,
			@RequestParam(name="formId",defaultValue="")String formId,
			@RequestParam(name="source",defaultValue="")String source,
			@RequestParam(name="purpose",defaultValue="")String purpose,
			@RequestParam(name="burning",defaultValue="")String burning,
			@RequestParam(name="protection",defaultValue="")String protection,
			@RequestParam(name="areaId",defaultValue="")Long areaId,
			@RequestParam(name="pageno",defaultValue="1")int pageno){
		Map<String, Object> tMp = new HashMap<>();
		Map<String, Object> mmp = null;
		Result rs = null;
		if(kindId==null||areaId==null){
			rs = Result.failure();
			rs.setObj(new ArrayList<>());
			rs.setMsg("品类和地址不能为NULL");
			return rs;
		}
		//参数传递
		tMp.put("countryType", countryType);
		tMp.put("kindId", kindId);
		tMp.put("colorId", colorId);
		tMp.put("formId", formId);
		tMp.put("source", source);
		tMp.put("purpose", purpose);
		tMp.put("burning", burning);
		tMp.put("protection", protection);
		tMp.put("protection", protection);
		
		List<String> areaIdsList = new ArrayList<>();
		Map<String, Object> areaIdsMap = null;
		//获取相关地址ID
		areaIdsMap = this.addressService.getAllChildID(areaId);
		Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
		if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<ezs_area> areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
			for (ezs_area tarea : areaListTemp) {
				areaIdsList.add(tarea.getId().toString());
			}
			tMp.put("areaIds", areaIdsList);
		}
		
		mmp = this.priceConditionService.getPriceInTime(tMp,pageno);
		List<PriceTrendIfo> plist = (List<PriceTrendIfo>) mmp.get("Obj");
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			rs = Result.success();
			rs.setObj(plist);			
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.parseInt(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * 根据地址节点的子节点获取父节点信息
	 * @param request
	 * @param response
	 * @param childId
	 * @return
	 */
	@RequestMapping("/getParentByChild")
	@ResponseBody
	public Object getParentByChild(HttpServletRequest request,HttpServletResponse response,Long childId){
		Result rs = null;
		Map<String, Object> mmp = this.addressService.getParentByChild(childId);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			ezs_area area = (ezs_area) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(area);
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg("参数传递有误");
		}
		return rs;
	}
	
	/**
	 * (废弃不用)
	 * 行情分析-二级栏目（年、月、周、日评）
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPriceAnalyzeTheme")
	@ResponseBody
	public Object getPriceAnalyzeTheme(HttpServletRequest request,HttpServletResponse response){
		List<ezs_column> elist = null;
		Result rs = null;
		Map<String, Object> mmp = this.priceConditionService.getSecondTheme(Long.valueOf(12));
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		elist = (List<ezs_column>) mmp.get("Obj");
		if(ErrorCode!=null&&(ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS))){
			rs = Result.success();
			rs.setObj(elist);
		}else{
			rs = Result.failure();
			rs.setErrorcode(ErrorCode);
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * (废弃不用)
	 * 行情分析（年、月、周、日评）
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPriceAnalyze")
	@ResponseBody
	public Object getPriceAnalyze(HttpServletRequest request,
			HttpServletResponse response,
			Long id,
			int currentPage
			)
	{
		Map<String, Object> mmp = null;
		List<ezs_ezssubstance> elist = null;
		Result rs = null;
		if(currentPage<=0)
			currentPage = 1;
		if(id==null){
			mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(12), currentPage);
		}else{
			mmp = this.industryInfoService.getIndustryInfoByKinds(id, currentPage);
		}
		ExPage page = (ExPage) mmp.get("Page");
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			elist = (List<ezs_ezssubstance>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(elist);
			rs.setMeta(page);
			
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	/**
	 * (废弃不用)
	 * 研究报告二级栏目
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getResearchReportTheme")
	@ResponseBody
	public Object getResearchReportTheme(HttpServletRequest request,HttpServletResponse response){
		List<ezs_column> elist = null;
		Result rs = null;
		Map<String, Object> mmp = this.priceConditionService.getSecondTheme(Long.valueOf(17));
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		elist = (List<ezs_column>) mmp.get("Obj");
		if(ErrorCode!=null&&(ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS))){
			rs = Result.success();
			rs.setObj(elist);
		}else{
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			rs.setMsg("参数传递有误");
		}
		return rs;
	}
	/**
	 * (废弃不用)
	 * 研究报告
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getResearchReport")
	@ResponseBody
	public Object getResearchReport(HttpServletRequest request,
			HttpServletResponse response,
			Long id,
			int currentPage){
		Map<String, Object> mmp = null;
		List<ezs_ezssubstance> elist = null;
		Result rs = null;
		if(currentPage<=0)
			currentPage = 1;
		if(id==null){
			mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(17), currentPage);
		}else{
			mmp = this.industryInfoService.getIndustryInfoByKinds(id, currentPage);
		}
		ExPage page = (ExPage) mmp.get("Page");
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			elist = (List<ezs_ezssubstance>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(elist);
			rs.setMeta(page);
			
		}else{
			rs = Result.failure();
			rs.setErrorcode(Integer.valueOf(mmp.get("ErrorCode").toString()));
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	
	
	
	/**
	 * 价格趋势+条件筛选
	 * @param request
	 * @param response
	 * @param kindId 类别
	 * @param colorId 颜色
	 * @param formId 形态
	 * @param source 来源
	 * @param purpose 用途
	 * @param burning 燃烧指数
	 * @param protection 是否环保
	 * @param areaId 定位地址
	 * @param currentPage 当前页码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/getPriceTrendcy")
	@ResponseBody
	public Object getPriceTrendcy(HttpServletRequest request,HttpServletResponse response,String kindId,String colorId
			,String formId,String source,String purpose,String burning,String protection,Long areaId,int currentPage){
		Map<String, Object> tMp = new HashMap<>();
		Map<String, Object> mmp = null;
		Result rs = null;
		//参数传递
		tMp.put("kindId", kindId);
		tMp.put("colorId", colorId);
		tMp.put("formId", formId);
		tMp.put("source", source);
		tMp.put("purpose", purpose);
		tMp.put("burning", burning);
		tMp.put("protection", protection);
		mmp = this.priceConditionService.getPriceTrendcy(tMp,currentPage);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<PriceTrendIfo> plist = (List<PriceTrendIfo>) mmp.get("Obj");
			rs = Result.success();
			rs.setObj(plist);			
		}else{
			rs = Result.failure();
			rs.setErrorcode(ErrorCode);
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
	
}
