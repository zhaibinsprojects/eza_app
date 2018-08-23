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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.index.service.AddressService;
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
	@Autowired
	private ezs_columnMapper columnMapper;
	@Autowired
	private ezs_ezssubstanceMapper ezs_ezssubstanceMapper;
	@Autowired
	private AddressService addressService;
	
	private static String view="/hangq/";
	
	private static Logger log = Logger.getLogger(HomeHangqIndexController.class);
	/**
	 * 价格行情首页   （mapper.xml文件sql须在数据完整后调整）
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
		//goodClassMap.put("109", "HIPS");
		//goodClassMap.put("110", "GPPS");
		//goodClassMap.put("111", "ABS");
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
		//newGoodClassTypeList.add("109");//HIPS
		//newGoodClassTypeList.add("110");//GPPS
		//newGoodClassTypeList.add("111");//ABS
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
			List<PriceTrendIfo> xplist = getPriceInTimeNew(gKinds);
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
		jReport.setReportList1(priceList);
		reportsList.add(jReport);
		//研究报告 
		ReportType yReport = new ReportType();
		yReport.setDate(new Date());
		yReport.setReportName("研究报告");
		yReport.setReportId(Long.valueOf(17));
		List<ezs_ezssubstance> reportList = getPriceReport(pageno,kindId);
		yReport.setReportList1(reportList);
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
	//再生料-实时报价
	public List<PriceTrendIfo> getPriceInTime(String kindId){
		List<PriceTrendIfo> plist = new ArrayList<>();
		Map<String, Object> mmp = this.priceConditionService.priceInTimeNew(Long.valueOf(kindId));
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			plist = (List<PriceTrendIfo>)mmp.get("Obj");
		}
		return plist;
	}
	
	//新料-实时报价
	public List<PriceTrendIfo> getPriceInTimeNew(String kindId){
		List<PriceTrendIfo> plist = new ArrayList<>();
		Map<String, Object> mmp = this.priceConditionService.priceInTimeNew2(Long.valueOf(kindId));
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
	
	/**
	 * app H5页面展示内容（价格评析、研究报告 分页展示，页面大小-10）
	 * @param type（priceAnalyse-价格评析，report-研究报告，priceInTime-实时报价）
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/analyseAndReport")
	public String analyseAndReport(@RequestParam(name="type",required=true)String type,
			@RequestParam(name="currentPage",required=false,defaultValue="1")int currentPage,Model model,HttpServletRequest request,
			@RequestParam(name="ecId",required=false,defaultValue="0")Long ecId
			){
		String showpages = "analyseAndRepore";
		Map<String, Object> resultMap = new HashMap<>();
		int pagesize = 10;
		if(currentPage<1) currentPage=1;
		if(type.trim().endsWith("priceAnalyse")){
			//展示价格评析
			resultMap = this.industryInfoService.getAllIndustryInfoByParentKinds2(Long.valueOf(12),ecId,currentPage,pagesize);
			//查询二级目录
			List<ezs_column> columnList = this.columnMapper.getSecondThemeByFirstTheme(Long.valueOf(12));
			resultMap.put("kinds", "priceAnalyse");
			resultMap.put("columnList", columnList);
		}else if(type.trim().endsWith("report")){
			//研究报告
			resultMap = this.industryInfoService.getAllIndustryInfoByParentKinds2(Long.valueOf(17),ecId,currentPage,pagesize);
			List<ezs_column> columnList = this.columnMapper.getSecondThemeByFirstTheme(Long.valueOf(17));
			resultMap.put("kinds", "report");
			resultMap.put("columnList", columnList);
		}
		model.addAttribute("resultMap",resultMap);
		return view+showpages;
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
		ezs_ezssubstance show = ezs_ezssubstanceMapper.selectByPrimaryKey(id);
		ezs_column column = columnMapper.selectByPrimaryKey(show.getEc_id());
		ezs_ezssubstance boytton = ezs_ezssubstanceMapper.getButtomOneSubstanceByid(id, column.getParentEzsColumn_id());
		ezs_ezssubstance top = ezs_ezssubstanceMapper.getTopOneSubstanceByid(id, column.getParentEzsColumn_id());
		model.addAttribute("top", top);
		model.addAttribute("button", boytton);
		model.addAttribute("show", show);
		model.addAttribute("title", column.getName());
		return view+"infoshow";
	}
	//实时报价-再生料/新料-列表（筛选条件）
	/**
	 * 
	 * @param type 新料/再生料  newclass/oldclass
	 * @param goodClassId
	 * @param areaId
	 * @param colorId
	 * @param formId
	 * @param currentPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getPriceInTime")
	@ResponseBody
	public Result getPriceInTime(@RequestParam(name="type",required=true)String type,
			@RequestParam(name="goodClassId",required=false,defaultValue="1")String goodClassId,
			@RequestParam(name="areaId",required=false) String areaId,
			@RequestParam(name="currentPage",required=false,defaultValue="1")int currentPage,
			@RequestParam(name="colorId",required=false)Long colorId,
			@RequestParam(name="formId",required=false)Long formId
			){
		Result rs = Result.success();
		Map<String, Object> resultMap = new HashMap<>();
		//参数传递
		Map<String, Object> tMp = new HashMap<>();
		if(colorId!=null)
			tMp.put("colorId", colorId);
		if(formId!=null)
			tMp.put("formId", formId);
		//获取相关地址ID
		List<String> areaIdsList = new ArrayList<>();
		Map<String, Object> areaIdsMap = null;
		if(areaId!=null){
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
		}
		if(type.equals("newclass")){
			//新料
			resultMap = this.priceConditionService.getPriceInTimeNew(tMp,currentPage,10);
		}else if(type.equals("oldclass")){
			//普通再生塑料
			resultMap = this.priceConditionService.getPriceInTimeOld(tMp,currentPage,10);
		}
		List<PriceTrendIfo> resultList = null;
		Integer ErrorCode = (Integer)resultMap.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			resultList = (List<PriceTrendIfo>)resultMap.get("Obj");
			rs.setObj(resultList);
		}else{
			rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			rs.setObj(resultList);
		}
		return rs;
	}
	//获取areaId的所有子标签（包含本标签）
	public List<Long> getAllChildrenAreaIDs(Long areaId){
		//获取相关地址ID
		List<Long> areaIdsList = new ArrayList<>();
		Map<String, Object> areaIdsMap = null;
		areaIdsMap = this.addressService.getAllChildID(areaId);
		Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
		if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<ezs_area> areaListTemp =new ArrayList<>();
			areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
			for (ezs_area tarea : areaListTemp) {
				areaIdsList.add(tarea.getId());
			}
		}
		return areaIdsList;
	}
	//跳转至实时报价详情页面（详情页面通过js加载数据）
	@RequestMapping(value="/priceInTimeDetailTurn")
	public String priceInTimeDetailTurn(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="priceId",required=true)String priceId,
			@RequestParam(name="type",required=true)String type,
			@RequestParam(name="dateBetweenType",required=false,defaultValue="WEEK") String dateBetweenType,Model model){
		
		model.addAttribute("dateBetweenType", "WEEK");
		model.addAttribute("priceId", priceId);
		model.addAttribute("type", type);
		return view+"priceintimedetail";
	}
	
	
	/**
	 * 实时报价详情页面(再生料、新料)(H5页面)
	 * @param priceId 实时价格ID
	 * @param type 新料/再生料  newclass/oldclass
	 * @param dateBetweenType 展示区间：一周 WEEK、一月 MONTH、一季度 QUARTER、一年 YEAR
	 * @return
	 */
	@RequestMapping(value="/priceInTimeDetail")
	public String priceInTimeDetail(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="priceId",required=true)String priceId,
			@RequestParam(name="type",required=true)String type,
			@RequestParam(name="dateBetweenType",required=false,defaultValue="WEEK") String dateBetweenType){
		Map<String,Object> mmp = new HashMap<>();
		//查询条件
		Map<String,Object> tMp = new HashMap<>();
		tMp.put("priceId", priceId);
		tMp.put("dateBetweenType", dateBetweenType);
		List<PriceTrendIfo> plist = new ArrayList<>();
		if(type!=null&&type.equals("newclass")){
			//新料详情
			mmp = this.priceConditionService.priceInTimeNewDetail(tMp);
			Integer ErrorCode = (Integer)mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				plist = (List<PriceTrendIfo>)mmp.get("Obj");
			}
		}else if(type!=null&&type.equals("oldclass")){
			//再生料详情查询
			mmp = this.priceConditionService.priceInTimeOldDetail(tMp);
			Integer ErrorCode = (Integer)mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				plist = (List<PriceTrendIfo>)mmp.get("Obj");
			}
		}
		return "";
	}
	/**
	 * 实时报价详情页面-分页展示(再生料、新料)
	 * @param priceId 实时价格ID
	 * @param type 新料/再生料  newclass/oldclass
	 * @param dateBetweenType 展示区间：一周 WEEK、一月 MONTH、一季度 QUARTER、一年 YEAR
	 * @return
	 */
	@RequestMapping(value="/priceInTimeDetailPage")
	public String priceInTimeDetailPage(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="priceId",required=true)String priceId,
			@RequestParam(name="type",required=true)String type,
			@RequestParam(name="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(name="dateBetweenType",required=false,defaultValue="WEEK") String dateBetweenType){
		//查询条件
		Map<String,Object> tMp = new HashMap<>();
		tMp.put("priceId", priceId);
		tMp.put("dateBetweenType", dateBetweenType);
		
		if(type!=null&&type.equals("newclass")){
			//新料详情
			this.priceConditionService.priceInTimeNewDetailPage(tMp, currentPage, 10);
		}else if(type!=null&&type.equals("oldclass")){
			//再生料详情查询
			this.priceConditionService.priceInTimeOldDetailPage(tMp, currentPage, 10);
		}
		return "";
	}
	//价格行情详情页面
	@RequestMapping(value="/priceAnalyDetail")
	public String priceAnalyDetail(){
		
		return "";
	}
	
}
