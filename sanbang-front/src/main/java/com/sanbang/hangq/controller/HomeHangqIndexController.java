package com.sanbang.hangq.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Tool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.hangq.servive.HangqAreaService;
import com.sanbang.index.service.AddressService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.index.service.ReportEssayServer;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.JsonUtils;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.Advices;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodClassType;
import com.sanbang.vo.HangqHomeMess;
import com.sanbang.vo.PriceInTimesVo;
import com.sanbang.vo.PriceTrendIfo;
import com.sanbang.vo.ReportType;
import com.sanbang.vo.hangq.AreaData;

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
	@Autowired
	private ezs_areaMapper areaMapper;
	
	private static String view="/hangq/";
	
	@Autowired
	private HangqAreaService hangqAreaService;
	
	/**
	 * 行情数据标识
	 */
	private  static final String HANGQ_DATA="HANGQ_INDEX_DATA";
	
	
	private static Logger log = Logger.getLogger(HomeHangqIndexController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/getHangqHomeCata")
	@ResponseBody
	public Result getHangqHomeCata(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(defaultValue="all")String reqtype){
		Result result = Result.success();
		result.setMsg("请求失败");
		Map<String, Object> map=new HashMap<>();
 		try {
 			RedisResult<Result> redate = (RedisResult<Result>) RedisUtils.get(HANGQ_DATA+reqtype,
 					Result.class);
 			if (redate.getCode() == RedisConstants.SUCCESS) {
				log.debug("查询redis分类成功执行");
				result=redate.getResult();
			} else {
					log.debug("查询redis分类执行失败");
					map=hangqAreaService.getHangqParamDate(reqtype, map);
					result.setSuccess(true);
			 		result.setMsg("请求成功");
			 		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			 		result.setObj(map);
			 		
			 		RedisUtils.get(HANGQ_DATA, Result.class);
					RedisResult<String> rrt;
					rrt = (RedisResult<String>) RedisUtils.set(HANGQ_DATA+reqtype, result,
						Long.valueOf(3600*24));
					if (rrt.getCode() == RedisConstants.SUCCESS) {
						log.debug("行情分类保存到redis成功执行");
					} else {
						log.debug("行情分类保存到redis失败");
					}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("系统错误！");
			result.setObj(map);
		}
 		if(result.getSuccess()) {
				Map<String, Object> map1=(Map<String, Object>) result.getObj();
				map1.remove("cata");
				result.setObj(map1);
			}
		return result;
	}

	/**
	 * common
	 * @return
	 */
	public Object commonToJson(List<ezs_dict> listp){
		List<Map<String, Object>> list=new ArrayList<>();
		 for (ezs_dict ezs_dict : listp) {
			Map<String, Object> map=new HashMap<>();
			map.put("id", ezs_dict.getId());
			map.put("name", ezs_dict.getName());
			list.add(map);
		}
		return JsonUtils.jsonOut(list); 
	}
	
	
	
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
		zsGoodClassType.setClassName("再生塑料");
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
		Map<String, Object> mmp = this.industryInfoService.getEzsTouTiao();
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
		advice01.setPath("https://m.ezaisheng.com/front/resource/images/app-banner01.png");
		//advice01.setPath("http://10.10.10.148/front/resource/images/app-banner01.png");
		advice01.setLink("https://m.ezaisheng.com/front/hangqtg001.html");
		//advice01.setPath("https://m.ezaisheng.com/front/resource/images/app-banner01.png");
		//advice01.setPath(baseurl+"/front/resource/images/app-banner01.png");
		advice01.setpName("app-banner01.png");

		adviceList.add(advice01);
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
	/**
	 * //实时报价-再生料/新料-列表（筛选条件）
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
			@RequestParam(name="goodClassId",required=false)String goodClassId,
			@RequestParam(name="areaId",required=false) String areaId,
			@RequestParam(name="currentPage",required=false,defaultValue="1")int currentPage,
			@RequestParam(name="colorId",required=false)String colorId,
			@RequestParam(name="formId",required=false)String formId,
			HttpServletRequest request
			){
		Result rs = Result.success();
		Map<String, Object> resultMap = new HashMap<>();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		//无需校验，未登录相当于没权限
		/*if(null==upi){
			List<PriceTrendIfo> resultListT = new ArrayList<>();
			rs = Result.failure();
			rs.setObj(resultListT);
			rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			rs.setMsg("用户未登录，不可查看");
			return rs;
		}*/
		//参数传递
		Map<String, Object> tMp = new HashMap<>();
		if(colorId!=null)
			tMp.put("colorId", colorId);/*可传多个 ， 号隔开*/
		if(formId!=null)
			tMp.put("formId", formId);
		if(goodClassId!=null)
			tMp.put("goodClassId", goodClassId);
		//获取相关地址ID
		List<String> areaIdsList = new ArrayList<>();
		Map<String, Object> areaIdsMap = null;
		try{
			if(areaId!=null&&!areaId.trim().equals("")&&Long.valueOf(areaId)>7){
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
			}else if(areaId!=null&&!areaId.trim().equals("")&&Long.valueOf(areaId)<=7){
				//大区查询
				List<String> areaIdList = AreaData.getprovinceids(Integer.valueOf(areaId));
				for (String provinceId : areaIdList) {
					areaIdsMap = this.addressService.getAllChildID(Long.valueOf(provinceId));
					Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
					if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
						List<ezs_area> areaListTemp =new ArrayList<>();
						areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
						for (ezs_area tarea : areaListTemp) {
							areaIdsList.add(tarea.getId().toString());
						}
					}
				}
				tMp.put("areaIds", areaIdsList);
			}
		}catch(Exception e){
			
		}
		if(type.equals("newclass")){
			//新料
			resultMap = this.priceConditionService.getPriceInTimeNew(tMp,currentPage,20);
		}else if(type.equals("oldclass")){
			//普通再生塑料
			resultMap = this.priceConditionService.getPriceInTimeOld(tMp,currentPage,20);
		}
		List<PriceTrendIfo> resultList = new ArrayList<>();
		Integer ErrorCode = (Integer)resultMap.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<PriceTrendIfo> returnList = new ArrayList<>();
			resultList = (List<PriceTrendIfo>)resultMap.get("Obj");
			for (PriceTrendIfo priceTrendIfo : resultList) {
				try{
					boolean showFlag = Tools.HangqValidate(upi,priceTrendIfo.getTrueGoodClassId());
					if(showFlag) {
						priceTrendIfo.setIsshow(1);
						}else {
							priceTrendIfo.setIsshow(0);
							}
				}catch(Exception e){
					e.printStackTrace();
					priceTrendIfo.setIsshow(0);
				}
				returnList.add(priceTrendIfo);
			}
			rs.setObj(returnList);
		}else{
			rs.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			rs.setObj(resultList);
		}
		return rs;
	}
	//获取areaId的所有子标签（包含本标签）
	@SuppressWarnings("unchecked")
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
		//获取token验证用户权限，符合-返回相关标志位
		model.addAttribute("showFlag", "1");
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
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/priceInTimeDetail")
	@ResponseBody
	public Object priceInTimeDetail(HttpServletRequest request,HttpServletResponse response,
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
		//集合反向排列
		Collections.reverse(plist);
		return plist;
	}
	/**
	 * 实时报价详情页面-分页展示(再生料、新料)
	 * @param priceId 实时价格ID
	 * @param type 新料/再生料  newclass/oldclass
	 * @param dateBetweenType 展示区间：一周 WEEK、一月 MONTH、一季度 QUARTER、一年 YEAR
	 * @return
	 */
	@RequestMapping(value="/priceInTimeDetailPage")
	@ResponseBody
	public Object priceInTimeDetailPage(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="priceId",required=true)String priceId,
			@RequestParam(name="type",required=true)String type,
			@RequestParam(name="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(name="dateBetweenType",required=false,defaultValue="WEEK") String dateBetweenType){
		Map<String,Object> mmp = new HashMap<>();
		//查询条件
		Map<String,Object> tMp = new HashMap<>();
		tMp.put("priceId", priceId);
		tMp.put("dateBetweenType", dateBetweenType);
		List<PriceTrendIfo> plist = new ArrayList<>();
		if(type!=null&&type.equals("newclass")){
			//新料详情
			mmp = this.priceConditionService.priceInTimeNewDetailPage(tMp, currentPage, 20);
			Integer ErrorCode = (Integer)mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				plist = (List<PriceTrendIfo>)mmp.get("Obj");
			}
		}else if(type!=null&&type.equals("oldclass")){
			//再生料详情查询
			mmp = this.priceConditionService.priceInTimeOldDetailPage(tMp, currentPage, 20);
			Integer ErrorCode = (Integer)mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				plist = (List<PriceTrendIfo>)mmp.get("Obj");
			}
		}
		//集合反向排列
		//Collections.reverse(plist);
		return plist;
	}
	
	//跳转至价格走势详情页面（详情页面通过js加载数据）
	@RequestMapping(value="/priceTrendDetailTurn")
	public String priceTrendDetailTurn(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="goodClassId",required=true)String goodClassId,
			@RequestParam(name="areaId",required=false)String areaId,
			@RequestParam(name="colorId",required=false)String colorId,
			@RequestParam(name="formId",required=false)String formId,
			@RequestParam(name="dateBetweenType",required=false,defaultValue="WEEK") String dateBetweenType,Model model){
		//获取token验证用户权限，符合-返回相关标志位
		model.addAttribute("dateBetweenType", dateBetweenType);
		model.addAttribute("goodClassId", goodClassId);
		model.addAttribute("areaId", areaId);
		model.addAttribute("colorId", colorId);
		model.addAttribute("formId", formId);
		//获取token
		model.addAttribute("token", request.getParameter("token"));			
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		boolean showFlag = false;
		try{
			showFlag = Tools.HangqValidate(upi,Long.valueOf(goodClassId));
		}catch(Exception e){}
		model.addAttribute("showFlag", showFlag==true?1:0);//0- 走势图不展示，1-走势图展示
		return view+"pricetrenddetail";
	}
	
	//价格走势详情页面
	@RequestMapping(value="/priceTrendDetail")
	@ResponseBody
	public Object priceTrendDetail(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="goodClassId",required=true)String goodClassId,
			@RequestParam(name="areaId",required=false)String areaId,
			@RequestParam(name="colorId",required=false)String colorId,
			@RequestParam(name="formId",required=false)String formId,
			@RequestParam(name="dateBetweenType",required=false,defaultValue="WEEK") String dateBetweenType){
		Map<String, Object> tMap = new HashMap<>();
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> ppList = new ArrayList<>();
		List<PriceTrendIfo> returnppList = new ArrayList<>();
		//获取用户信息
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		
		if(goodClassId!=null&&!goodClassId.trim().equals(""))
			tMap.put("kindId", goodClassId);
		if(colorId!=null&&!colorId.trim().equals(""))
			tMap.put("colorId", colorId);
		if(formId!=null&&!formId.trim().equals(""))
			tMap.put("formId", formId);
		tMap.put("dateBetweenType", dateBetweenType);
		//获取相关地址ID
		List<String> areaIdsList = new ArrayList<>();
		Map<String, Object> areaIdsMap = null;
		//记录筛选根地址
		ezs_area areaRoot = null;
		try{
			if(areaId!=null&&!areaId.trim().equals("")&&Long.valueOf(areaId)>7){
				areaRoot = this.areaMapper.selectByPrimaryKey(Long.valueOf(areaId));
				areaIdsMap = this.addressService.getAllChildID(Long.valueOf(areaId));
				Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
				if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
					List<ezs_area> areaListTemp =new ArrayList<>();
					areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
					for (ezs_area tarea : areaListTemp) {
						areaIdsList.add(tarea.getId().toString());
					}
					tMap.put("areaIds", areaIdsList);
				}
			}else if(areaId!=null&&!areaId.trim().equals("")&&Long.valueOf(areaId)<=7){
				//大区查询
				areaRoot = new ezs_area();
				areaRoot.setId(Long.valueOf(areaId));
				log.info("areaId-------------------大区ID:"+areaId);
				List<String> areaIdList = AreaData.getprovinceids(Integer.valueOf(areaId));
				for (String provinceId : areaIdList) {
					areaIdsMap = this.addressService.getAllChildID(Long.valueOf(provinceId));
					Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
					if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
						List<ezs_area> areaListTemp =new ArrayList<>();
						areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
						for (ezs_area tarea : areaListTemp) {
							areaIdsList.add(tarea.getId().toString());
						}
					}
				}
				tMap.put("areaIds", areaIdsList);
			}
		}catch(Exception e){  }
		
		mmp = this.priceConditionService.getPriceTrendcyNew(tMap, 0, 20);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			ppList = (List<PriceTrendIfo>)mmp.get("Obj");
			//地址判断
			//地址信息
			String areaNameTemp = null;
			Long areaIdTemp = 0L;
			if(areaRoot!=null&&areaRoot.getId()>7){
				areaNameTemp = areaRoot.getAreaName();
				areaIdTemp = areaRoot.getId();
			}else if(areaRoot!=null&&areaRoot.getId()<=7){
				//此为大区,获取大区的name
				areaNameTemp = AreaData.getAreaName(areaRoot.getId());
				areaIdTemp = areaRoot.getId();
				log.info("areaName-------------------大区Name:"+areaNameTemp);
			}
			for (PriceTrendIfo priceTrendIfo : ppList) {
				try{
					boolean showFlag = Tools.HangqValidate(upi,Long.valueOf(goodClassId));
					if(showFlag)
						priceTrendIfo.setIsshow(1);
					else
						priceTrendIfo.setIsshow(0);
				}catch(Exception e){
					priceTrendIfo.setIsshow(0);
				}
				
				//地址信息,若未利用地址参数进行筛选
				if(areaId!=null&&!areaId.trim().equals("")){
					priceTrendIfo.setGoodArea(areaNameTemp);
					priceTrendIfo.setRegion_id(areaIdTemp);
					log.info("areaName-------------------大区Name:"+areaNameTemp);
				}else{
					String areaName = getAreaName(priceTrendIfo.getRegion_id());
					priceTrendIfo.setGoodArea(areaName);
				}
				
				returnppList.add(priceTrendIfo);
			}
		}
		Collections.reverse(returnppList);
		return returnppList;
	}
	//价格走势详情页面-分页加载更多
	@RequestMapping(value="/priceTrendDetailPage")
	@ResponseBody
	public Object priceTrendDetailPage(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="goodClassId",required=true)String goodClassId,
			@RequestParam(name="areaId",required=false)String areaId,
			@RequestParam(name="colorId",required=false)String colorId,
			@RequestParam(name="formId",required=false)String formId,
			@RequestParam(name="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(name="dateBetweenType",required=false,defaultValue="WEEK") String dateBetweenType){
		Map<String, Object> tMap = new HashMap<>();
		Map<String, Object> mmp = new HashMap<>();
		List<PriceTrendIfo> ppList = new ArrayList<>();
		List<PriceTrendIfo> returnppList = new ArrayList<>();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		
		areaId = request.getParameter("areaId");
		log.info("areaId-------------------------:"+areaId);
		
		if(goodClassId!=null&&!goodClassId.trim().equals(""))
			tMap.put("kindId", goodClassId);
		if(colorId!=null&&!colorId.trim().equals(""))
			tMap.put("colorId", colorId);
		if(formId!=null&&!formId.trim().equals(""))
			tMap.put("formId", formId);
		tMap.put("currentPage", currentPage);
		tMap.put("dateBetweenType", dateBetweenType);
		//获取相关地址ID
		List<String> areaIdsList = new ArrayList<>();
		Map<String, Object> areaIdsMap = null;
		ezs_area areaRoot = null;
		try{
			if(areaId!=null&&!areaId.trim().equals("")&&Long.valueOf(areaId)>7){
				areaRoot = this.areaMapper.selectByPrimaryKey(Long.valueOf(areaId));
				areaIdsMap = this.addressService.getAllChildID(Long.valueOf(areaId));
				Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
				if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
					List<ezs_area> areaListTemp =new ArrayList<>();
					areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
					for (ezs_area tarea : areaListTemp) {
						areaIdsList.add(tarea.getId().toString());
					}
					tMap.put("areaIds", areaIdsList);
				}
			}else if(areaId!=null&&!areaId.trim().equals("")&&Long.valueOf(areaId)<=7){
				//大区查询
				areaRoot = new ezs_area();
				areaRoot.setId(Long.valueOf(areaId));
				List<String> areaIdList = AreaData.getprovinceids(Integer.valueOf(areaId));
				for (String provinceId : areaIdList) {
					areaIdsMap = this.addressService.getAllChildID(Long.valueOf(provinceId));
					Integer AreaErrorCode = (Integer) areaIdsMap.get("ErrorCode");
					if(AreaErrorCode!=null&&AreaErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
						List<ezs_area> areaListTemp =new ArrayList<>();
						areaListTemp = (List<ezs_area>) areaIdsMap.get("Obj");
						for (ezs_area tarea : areaListTemp) {
							areaIdsList.add(tarea.getId().toString());
						}
					}
				}
				tMap.put("areaIds", areaIdsList);
			}
		}catch(Exception e){  }
		
		mmp = this.priceConditionService.getPriceTrendcyNewPage(tMap,currentPage,20);
		Integer ErrorCode = (Integer)mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			ppList = (List<PriceTrendIfo>)mmp.get("Obj");
			//地址信息
			String areaNameTemp = null;
			Long areaIdTemp = 0L;
			if(areaRoot!=null&&areaRoot.getId()>7){
				areaNameTemp = areaRoot.getAreaName();
				areaIdTemp = areaRoot.getId();
			}else if(areaRoot!=null&&areaRoot.getId()<=7){
				//此为大区,获取大区的name
				areaNameTemp = AreaData.getAreaName(areaRoot.getId());
				areaIdTemp = areaRoot.getId();
				log.info("大区名称---------------------------:"+areaNameTemp);
			}
			for (PriceTrendIfo priceTrendIfo : ppList) {
				try{
					boolean showFlag = Tools.HangqValidate(upi,Long.valueOf(goodClassId));
					if(showFlag)
						priceTrendIfo.setIsshow(1);
					else
						priceTrendIfo.setIsshow(0);
				}catch(Exception e){
					priceTrendIfo.setIsshow(0);
				}
				//地址信息,若未利用地址参数进行筛选
				if(areaId!=null&&!areaId.trim().equals("")){
					priceTrendIfo.setGoodArea(areaNameTemp);
					priceTrendIfo.setRegion_id(areaIdTemp);
				}else{
					String areaName = getAreaName(priceTrendIfo.getRegion_id());
					priceTrendIfo.setGoodArea(areaName);
				}
				returnppList.add(priceTrendIfo);
			}
		}
		return returnppList;
	}
	/**
	 * 获取地址名称：XX市
	 * @param areaId
	 * @return
	 */
	private String getAreaName(Long areaId){
		ezs_area areaTemp = this.areaMapper.selectByPrimaryKey(areaId);
		String areaName = areaTemp.getAreaName();
		while(areaTemp.getLevel()>1){
			areaTemp = this.areaMapper.selectByPrimaryKey(areaTemp.getParent_id());
			areaName = areaTemp.getAreaName();
		}
		return areaName;
	}
}
