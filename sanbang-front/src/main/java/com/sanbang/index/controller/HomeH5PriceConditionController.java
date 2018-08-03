package com.sanbang.index.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_column;
import com.sanbang.bean.ezs_ezssubstance;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.dao.ezs_columnMapper;
import com.sanbang.dao.ezs_ezssubstanceMapper;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.index.service.AddressService;
import com.sanbang.index.service.IndustryInfoService;
import com.sanbang.index.service.PriceConditionService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.PriceTrendIfo;
import com.sanbang.vo.Series;

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
	private ezs_columnMapper columnMapper;
	@Autowired
	private ezs_goods_classMapper goodsClassMapper;
	@Value("${config.ezsSubstance.pdfurl}")
	private String ezsSubstanceUrl;	
	@Value("${config.h5ezsSubstance.pdfurl}")
	private String ezsh5SubstanceUrl;
	
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
		int pagesize = 3;
		//行情分析
		Map<String, Object>  jiage = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(jiagecatid), pageno,pagesize);
		//研究报告 
		Map<String, Object>  baogao = this.industryInfoService.getAllIndustryInfoByParentKinds(Long.valueOf(yanjiucatid), pageno,pagesize);
		
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
		//zoushi = this.priceConditionService.getPriceTrendcy(tMp,pageno,pagesize);
		baojia = this.priceConditionService.getPriceInTime(tMp,pageno,pagesize);
		
		model.addAttribute("jiage", jiage);
		model.addAttribute("baogao", baogao);
		/*model.addAttribute("zoushi", zoushi);*/
		model.addAttribute("baojia", baojia);
		model.addAttribute("baojia_goodclass", kindId);
		model.addAttribute("baojia_areaId", areaId);
		
		return view+"/hangqindex";
	}
	
	@RequestMapping(value="/getPriceTrendcyShow")
	@ResponseBody
	public Object getPriceTrendcyShow(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(name="kindId",defaultValue="1") String kindId,
			@RequestParam(name="currentPage",defaultValue="1") int pageno){
		List<String> dateList = new ArrayList<>();
		JSONObject json=new JSONObject();
		Series seriesVo = new Series();
		List<BigDecimal> priceList = new ArrayList<>();
		Map<String,Object> mmp = new HashMap<>();
		Map<String, Object> tMp = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		//参数传递
		tMp.put("kindId", kindId);
		//修改为取近一个月数据
		mmp = this.priceConditionService.getPriceTrendcy(tMp,pageno,10);
		Integer ErrorCode = (Integer) mmp.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			List<PriceTrendIfo> plist = (List<PriceTrendIfo>) mmp.get("Obj");
			if(plist.size()<=0){
				try {
		        	response.setContentType("text/html;charset=utf-8");
					response.getWriter().write("");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			for (PriceTrendIfo priceTrendIfo : plist) {
				DecimalFormat df = new DecimalFormat("0.000");
				
				//priceList.add(BigDecimal.valueOf(priceTrendIfo.getCurrentAVGPrice()));
				priceList.add(new BigDecimal(df.format(priceTrendIfo.getCurrentAVGPrice())));
				try {
					Date date = sdf.parse(priceTrendIfo.getDealDate());
					dateList.add(sdf2.format(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			ezs_goods_class goodsClass = goodsClassMapper.selectByPrimaryKey(Long.valueOf(kindId));
			//对列表进行转向处理
			Collections.reverse(priceList);
			Collections.reverse(dateList);
			seriesVo.setData(priceList);
			seriesVo.setName(goodsClass.getName());
	        json.put("name", dateList);
	        json.put("data", seriesVo);
	        try {
	        	response.setContentType("text/html;charset=utf-8");
				response.getWriter().write(json.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//无数据
			try {
	        	response.setContentType("text/html;charset=utf-8");
				response.getWriter().write("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	
	/**
	 * app H5页面展示内容（价格评析、研究报告、实时报价，分页展示，页面大小-10）
	 * @param type（priceAnalyse-价格评析，report-研究报告，priceInTime-实时报价）
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/analyseAndReport")
	public String analyseAndReport(@RequestParam(name="type",required=true)String type,
			@RequestParam(name="goodClassId",required=false,defaultValue="1")String goodClassId,
			@RequestParam(name="areaId",required=false,defaultValue="4523541") String areaId,
			@RequestParam(name="ecId",required=false,defaultValue="0")Long ecId,
			int currentPage,Model model){
		String showpages = "analyseAndRepore";
		Map<String, Object> resultMap = new HashMap<>();
		int pagesize = 10;
		if(currentPage<1) currentPage=1;
		if(type.trim().endsWith("priceAnalyse")){
			//展示价格评析
			resultMap = this.industryInfoService.getAllIndustryInfoByParentKinds2(Long.valueOf(12),ecId,currentPage,pagesize);
			//查询二级目录
			List<ezs_column> columnListTemp = new ArrayList<>();
			List<ezs_column> columnList = this.columnMapper.getSecondThemeByFirstTheme(Long.valueOf(12));
			resultMap.put("kinds", "priceAnalyse");
			resultMap.put("columnList", columnList);
		}else if(type.trim().endsWith("report")){
			//研究报告
			resultMap = this.industryInfoService.getAllIndustryInfoByParentKinds2(Long.valueOf(17),ecId,currentPage,pagesize);
			List<ezs_column> columnList = this.columnMapper.getSecondThemeByFirstTheme(Long.valueOf(17));
			resultMap.put("kinds", "report");
			resultMap.put("columnList", columnList);
		}else if(type.trim().endsWith("priceInTime")){
			//参数传递
			Map<String, Object> tMp = new HashMap<>();
			tMp.put("kindId", goodClassId);
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
			resultMap = this.priceConditionService.getPriceInTime(tMp,currentPage,pagesize);
			resultMap.put("goodClassId", goodClassId);
			resultMap.put("areaId", areaId);
			resultMap.put("kinds", "priceInTime");
			//实时报价
			showpages = "priceintime";
		}
		model.addAttribute("resultMap",resultMap);
		return view+showpages;
	}
	
	/**
	 * app H5页面展示内容（价格评析、研究报告、实时报价，分页展示，页面大小-10）
	 * @param type（priceAnalyse-价格评析，report-研究报告，priceInTime-实时报价）
	 * @param model
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/analyseAndReportPage")
	public String analyseAndReportPage(@RequestParam(name="type",required=true)String type,
			@RequestParam(name="goodClassId",required=false,defaultValue="1")String goodClassId,
			@RequestParam(name="areaId",required=false,defaultValue="4523541") String areaId,
			@RequestParam(name="ecId",required=false,defaultValue="0")Long ecId,
			int currentPage,Model model){
		String showpages = "analyseAndReporePage";
		Map<String, Object> resultMap = new HashMap<>();
		int pagesize = 10;
		if(currentPage<1) currentPage=1;
		if(type.trim().endsWith("priceAnalyse")){
			//展示价格评析
			resultMap = this.industryInfoService.getAllIndustryInfoByParentKinds2(Long.valueOf(12),ecId,currentPage,pagesize);
			resultMap.put("kinds", "priceAnalyse");
		}else if(type.trim().endsWith("report")){
			//研究报告
			resultMap = this.industryInfoService.getAllIndustryInfoByParentKinds2(Long.valueOf(17),ecId,currentPage,pagesize);
			resultMap.put("kinds", "report");
		}else if(type.trim().endsWith("priceInTime")){
			//参数传递
			Map<String, Object> tMp = new HashMap<>();
			tMp.put("kindId", goodClassId);
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
			resultMap = this.priceConditionService.getPriceInTime(tMp,currentPage,pagesize);
			//实时报价
			resultMap.put("kinds", "priceInTime");
			showpages = "priceintimePage";
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
		ezs_ezssubstance show=ezs_ezssubstanceMapper.selectByPrimaryKey(id);
		ezs_column column=columnMapper.selectByPrimaryKey(show.getEc_id());
		model.addAttribute("show", show);
		model.addAttribute("title", column.getName());
		return view+"ezsintroduce";
	}
	
	
	/**
	 * 复制下载文件
	 * @param id
	 * @return
	 */
	@RequestMapping("/getFileForHangq")
	@ResponseBody
	public Result  getFileForHangq(long id){
		Result result=Result.failure();
		result.setSuccess(false);
		result.setMsg("查看失败");
		result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		try {
			ezs_ezssubstance	show=ezs_ezssubstanceMapper.selectByPrimaryKey(id);
			String  oldFilePath=ezsSubstanceUrl+show.getAttachment();
			String  newFilePath=ezsh5SubstanceUrl+show.getId()+".pdf";
			log.info("oldurl：=="+oldFilePath);
			log.info("newurl：=="+newFilePath);
			File srcFile=new File(oldFilePath);
			File destFile=new File(newFilePath);
			if(!srcFile.exists()){
				result.setSuccess(false);
				result.setMsg("查看失败");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				return  result;
			}
			
			if(!destFile.exists()){
				if(/*destFile.mkdirs()&&*/destFile.createNewFile()){
					FileUtil.copyFile(srcFile, destFile);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					result.setSuccess(true);
					result.setMsg("查看成功");
				}else{
					result.setSuccess(false);
					result.setMsg("查看失败");
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				}
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setSuccess(true);
				result.setMsg("查看成功");
			}
		} catch (IOException e) {
			e.printStackTrace();
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}
	
}
