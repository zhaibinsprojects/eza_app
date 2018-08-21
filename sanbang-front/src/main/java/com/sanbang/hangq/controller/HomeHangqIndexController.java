package com.sanbang.hangq.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.utils.Result;
import com.sanbang.vo.Advices;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodClassType;
import com.sanbang.vo.HangqHomeMess;
import com.sanbang.vo.PriceInTimesVo;
import com.sanbang.vo.PriceTrendIfo;
import com.sanbang.vo.ReportType;

@Controller
@RequestMapping("/app/hangq/")
public class HomeHangqIndexController {
	@Autowired
	private PriceConditionService priceConditionService;
	@Autowired
	private IndustryInfoService industryInfoService;
	
	private static Logger log = Logger.getLogger(HomeHangqIndexController.class);
	/**
	 * 价格行情首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getHangqHomeMess")
	@ResponseBody
	public Result getHangqHomeMess(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="currentPage",defaultValue="1") int pageno,
			@RequestParam(name="kindId",defaultValue="1") String kindId,/*品类*/
			@RequestParam(name="areaId",required=false) Long areaId,/*地区*/
			@RequestParam(name="formId",required=false) Long formId,/*形态*/
			@RequestParam(name="colorId",required=false) Long colorId,/*颜色*/
			@RequestParam(name="dateBetweenType",required=true,defaultValue="WEEK") String dateBetweenType/*展示区间：一周、一月、一季度、一年*/
			){
		Result rs = Result.success();
		HangqHomeMess hqm = new HangqHomeMess();
		Map<String, String> goodClassMap = new HashMap<>();
		goodClassMap.put("3", "ABS");
		goodClassMap.put("4", "PE");
		goodClassMap.put("5", "PP");
		goodClassMap.put("6", "PS");
		goodClassMap.put("7", "PVC");
		
		goodClassMap.put("104", "LLDPE");
		goodClassMap.put("105", "LDPE");
		goodClassMap.put("106", "HDPE");
		goodClassMap.put("107", "PP");
		goodClassMap.put("108", "PVC");
		goodClassMap.put("109", "HIPS");
		goodClassMap.put("110", "GPPS");
		goodClassMap.put("111", "ABS");
		//再生类
		List<String> goodClassTypeList = new ArrayList<>();
		goodClassTypeList.add("3");//ABS
		goodClassTypeList.add("4");//PE
		goodClassTypeList.add("5");//PP
		goodClassTypeList.add("6");//PS
		goodClassTypeList.add("7");//PVC
		//新料
		
		List<String> newGoodClassTypeList = new ArrayList<>();
		newGoodClassTypeList.add("104");//LLDPE
		newGoodClassTypeList.add("105");//LDPE
		newGoodClassTypeList.add("106");//HDPE
		newGoodClassTypeList.add("107");//PP
		newGoodClassTypeList.add("108");//PVC
		newGoodClassTypeList.add("109");//HIPS
		newGoodClassTypeList.add("110");//GPPS
		newGoodClassTypeList.add("111");//ABS
		//banner 返回图片和连接
		List<Advices> advices = getAdvicesInfo();
		hqm.setAdviceList(advices);
		//小易头条
		List<ezs_ezssubstance> toutiao = getTouTiao();
		hqm.setTouTiaoList(toutiao);
		//实时报价  再生类(ABS、PP、PE、PS、PVC)
		List<PriceInTimesVo> zspitvoList = new ArrayList<>();
		List<GoodClassType> baojia = new ArrayList<>();
		GoodClassType zsGoodClassType = new GoodClassType();
		for (String gKinds : goodClassTypeList) {
			PriceInTimesVo pitvo = new PriceInTimesVo();
			List<PriceTrendIfo> zsplist = getPriceInTime(gKinds);
			pitvo.setGoodClassId(gKinds);
			pitvo.setPriceList(zsplist);
			pitvo.setGoodClassName(goodClassMap.get(gKinds));
			zspitvoList.add(pitvo);
		}
		zsGoodClassType.setClassId(Long.valueOf(1));
		zsGoodClassType.setClassName("再生类塑料");
		zsGoodClassType.setDate(new Date());
		zsGoodClassType.setPriceInTimeList(zspitvoList);
		baojia.add(zsGoodClassType);
		//实时报价  新料(ABS、PP、PE、PS、PVC)
		GoodClassType xGoodClassType = new GoodClassType();
		List<PriceInTimesVo> xpitvoList = new ArrayList<>();
		for (String gKinds : newGoodClassTypeList) {
			PriceInTimesVo pitvo = new PriceInTimesVo();
			List<PriceTrendIfo> xplist = getPriceInTime(gKinds);
			pitvo.setGoodClassId(gKinds);
			pitvo.setPriceList(xplist);
			pitvo.setGoodClassName(goodClassMap.get(gKinds));
			xpitvoList.add(pitvo);
		}
		xGoodClassType.setClassId(Long.valueOf(101));
		xGoodClassType.setClassName("新料");
		xGoodClassType.setDate(new Date());
		xGoodClassType.setPriceInTimeList(xpitvoList);
		baojia.add(xGoodClassType);
		hqm.setBaojia(baojia);
		//价格走势  (ABS、PP、PE、PS、PVC)
		List<PriceInTimesVo> priceTrendList = new ArrayList<>();
		for (String gKinds : goodClassTypeList) {
			List<PriceTrendIfo> plist = getPriceTrend(pageno,gKinds,areaId,formId,colorId,dateBetweenType);
			PriceInTimesVo pricevo = new PriceInTimesVo();
			pricevo.setGoodClassName(goodClassMap.get(gKinds));
			pricevo.setGoodClassId(gKinds);
			pricevo.setPriceList(plist);
			priceTrendList.add(pricevo);
		}
		hqm.setPriceTrend(priceTrendList);
		//hqm.setSeries(series);
		//价格评析
		List<ReportType> reportsList = new ArrayList<>();
		ReportType jReport = new ReportType();
		jReport.setDate(new Date());
		jReport.setReportName("价格评析");
		jReport.setReportId(Long.valueOf(12));
		List<ezs_ezssubstance> priceList = getPriceAnalyse(pageno,kindId);
		jReport.setReportList(priceList);
		reportsList.add(jReport);
		//研究报告 
		ReportType yReport = new ReportType();
		yReport.setDate(new Date());
		yReport.setReportName("研究报告");
		yReport.setReportId(Long.valueOf(17));
		List<ezs_ezssubstance> reportList = getPriceReport(pageno,kindId);
		yReport.setReportList(reportList);
		reportsList.add(yReport);
		hqm.setReportList(reportsList);
		rs.setObj(hqm);
		return rs;
	}
	//价格趋势
	@SuppressWarnings("unchecked")
	public List<PriceTrendIfo> getPriceTrend(int pageno, String kindId, Long areaId, Long formId, Long colorId, String dateBetweenType){
		List<PriceTrendIfo> plist = null;
		//返回數據容器
		Map<String,Object> mmp = new HashMap<>();
		//查詢條件
		Map<String, Object> tMp = new HashMap<>();
		//参数传递
		tMp.clear();
		tMp.put("kindId", kindId);
		tMp.put("formId", formId);
		tMp.put("colorId", colorId);
		//tMp.put("dateBetweenType", dateBetweenType);
		tMp.put("dateBetweenType", "MONTH");
		//修改为取近一个月数据
		mmp = this.priceConditionService.getPriceTrendcyNew(tMp,pageno,10);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			plist = (List<PriceTrendIfo>) mmp.get("Obj");
			//对列表进行转向处理
			Collections.reverse(plist);
		}
		return plist;
	}
	//价格评析
	@SuppressWarnings("unchecked")
	public List<ezs_ezssubstance> getPriceAnalyse(int pageno, String kindId){
		List<ezs_ezssubstance> glist = null;
		List<ezs_ezssubstance> glistTemp = new ArrayList<>();
		Map<String, Object> mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(12), pageno,3);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<ezs_ezssubstance>)mmp.get("Obj");
			for (ezs_ezssubstance ezss : glist) {
				ezs_ezssubstance ezssTemp = new ezs_ezssubstance();
				ezssTemp.setMeta(ezss.getMeta());
				ezssTemp.setName(ezss.getName());
				ezssTemp.setId(ezss.getId());
				glistTemp.add(ezssTemp);
			}
		}
		return glistTemp;
	}
	//研究报告
	@SuppressWarnings("unchecked")
	public List<ezs_ezssubstance> getPriceReport(int pageno, String kindId){
		List<ezs_ezssubstance> glist = null;
		List<ezs_ezssubstance> glistTemp = new ArrayList<>();
		Map<String, Object> mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(17), pageno,3);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<ezs_ezssubstance>)mmp.get("Obj");
			for (ezs_ezssubstance ezss : glist) {
				ezs_ezssubstance ezssTemp = new ezs_ezssubstance();
				ezssTemp.setMeta(ezss.getMeta());
				ezssTemp.setName(ezss.getName());
				ezssTemp.setId(ezss.getId());
				glistTemp.add(ezssTemp);
			}
		}
		return glistTemp;
	}
	//头条
	@SuppressWarnings("unchecked")
	public List<ezs_ezssubstance> getTouTiao(){
		List<ezs_ezssubstance> glist = null;
		List<ezs_ezssubstance> glistTemp = new ArrayList<>();
		Map<String, Object> mmp = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(12),1,3);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			glist = (List<ezs_ezssubstance>)mmp.get("Obj");
			for (ezs_ezssubstance ezss : glist) {
				ezs_ezssubstance ezssTemp = new ezs_ezssubstance();
				ezssTemp.setMeta(ezss.getMeta());
				ezssTemp.setName(ezss.getName());
				ezssTemp.setId(ezss.getId());
				glistTemp.add(ezssTemp);
			}
		}
		return glistTemp;
	}
	//实时报价
	public List<PriceTrendIfo> getPriceInTime(String kindId){
		List<PriceTrendIfo> plist = new ArrayList<>();
		Map<String, Object> mmp = this.priceConditionService.priceInTimeNew(Long.valueOf(kindId));
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			plist = (List<PriceTrendIfo>)mmp.get("Obj");
		}
		return plist;
	}
	/**
	 * 广告-活动展示时可启用，
	 * @author zhaibin
	 * @return
	 */
	private List<Advices> getAdvicesInfo(){
		List<Advices> adviceList = new ArrayList<Advices>();
		Advices advice01 = new Advices();
		advice01.setPath("https://m.ezaisheng.com/front/resource/indeximg/title005.jpg");
		advice01.setLink("");
		advice01.setpName("title005.jpg");
		
		Advices advice02 = new Advices();
		advice02.setPath("https://m.ezaisheng.com/front/resource/indeximg/title006.jpg");
		advice02.setLink("");
		advice02.setpName("title006.jpg");
		adviceList.add(advice01);
		adviceList.add(advice02);
		return adviceList;
	}
}
