package com.sanbang.goods.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.alibaba.fastjson.JSONArray;
import com.itextpdf.text.pdf.BaseFont;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.upload.sevice.FileUploadService;
import com.sanbang.upload.sevice.impl.FileUploadServiceImpl;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.CurrencyClass;
import com.sanbang.vo.DictionaryCode;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Resource(name="fileUploadService")
	private FileUploadService fileUploadService;
	// 日志
	private static Logger log = Logger.getLogger(FileUploadServiceImpl.class);

	/**
	 * 查询货品详情（描述说明也走这方法，以及在下订单时候，往前台返回商品单价用以计算总价、商品库存量，也是走这个方法，都从从商品详情中取）
	 * @param request
	 * @param id 货品id
	 * @return
	 */
	@RequestMapping("/goodsDetail")
	@ResponseBody
	public Result getGoodsDetail(HttpServletRequest request,Long id){
		Result result = new Result();
		ezs_goods goods = goodsService.getGoodsDetail(id);
		if(null != goods){
			result.setObj(goods);
			result.setMsg("查询成功！");
			result.setSuccess(true);
		}else{
			result.setMsg("查询失败！");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 查询货品评价列表
	 * @param request
	 * @param id 货品id
	 * @return
	 */
	@RequestMapping("/listForEvaluate")
	@ResponseBody
	public Result listForEvaluate(HttpServletRequest request,Long id){
		Result result = new Result();
		List<ezs_dvaluate> list  = new ArrayList<ezs_dvaluate>();
		list = goodsService.listForEvaluate(id);
		if(null != list && list.size()>0){
			result.setObj(list);
			result.setMsg("查询成功");
			result.setSuccess(true);
		}else{
			result.setMsg("查询失败");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 当第一次收藏时，是insert,取消收藏时是update更新状态（货品收藏）
	 * @param request
	 * @param goodId	商品id
	 * @return
	 */
	@RequestMapping("/updateShare")
	@ResponseBody
	public Result updateShare(HttpServletRequest request,Long goodId){
		Result result = new Result();
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if(null != goodId){
			ezs_documentshare share = goodsService.getCollect(goodId);
			if(null != share){
				if(share.getDeleteStatus().equals(true)){
					goodsService.updateCollect(goodId,false);
					result.setMsg("取消收藏");
				}else{
					goodsService.updateCollect(goodId,true);
					result.setMsg("收藏成功");
				}
			}else{
				try{
					goodsService.insertCollect(goodId,user.getId());
					result.setMsg("已收藏");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}else{
			result.setMsg("收藏出错");
		}
		return result;
	}
	
	/**
	 * 加入采购单（加入购物车）
	 * @param request
	 * @param goodsCartList
	 * @return
	 */
	@RequestMapping("/insertCart")
	@ResponseBody
	public Result insertCart(HttpServletRequest request,HttpServletResponse response,ezs_goodscart goodscart){
		Result result = null;
		//modify start by zhaibin 2018/05/31
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if(user==null){
			result = Result.failure();
			result.setMsg("用户未登录");
			return result;
		}
		//modify end by zhaibin 2018/05/31
		ezs_bill bill = user.getEzs_bill();
		int n;
		n = goodsService.insertCart(goodscart);
		if(n>0){
			result = Result.success();
			result.setMsg("添加成功");
		}else{
			result = Result.failure();
			result.setMsg("添加失败");
		}
		return result;
	}
	
	/**
	 * 立即购买（加入订单）
	 * @param request
	 * @param order
	 * @return
	 */
	@RequestMapping("/insertOrder")
	@ResponseBody
	public Result insertOrder(HttpServletRequest request,HttpServletResponse response,ezs_orderform order){
		Result result = null;
		int n;
		// modify start by zhaibin 2018/05/31
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			result = Result.failure();
			result.setMsg("用户未登录");
			return result;
		}
		// modify end by zhaibin 2018/05/31
		order.setAddTime(new Date());
		order.setDeleteStatus(false);
		n = goodsService.insertOrder(order);
		if(n>0){
			result = Result.success();
			result.setMsg("添加成功");
			result.setSuccess(true);
		}
		return result;
	}
	/**
	 * 采购单列表（就是预约定制的列表）
	 * @param request
	 * @param user_id
	 * @return
	 */
	@RequestMapping("/customizedList")
	@ResponseBody
	public Result customizedList(HttpServletRequest request,Long user_id){
		List<ezs_customized> list = new ArrayList<ezs_customized>();
		Result result = new Result();
		list =  goodsService.customizedList(user_id);
		if(null != list && list.size()>0){
			result.setObj(list);
			result.setSuccess(true);
			result.setMsg("查询成功");
		}else{
			result.setSuccess(false);
			result.setMsg("采购单为空");
		}
		return result;
	}
	
	/**
	 * 预约预定
	 * @param request
	 * @param customized 预约实体类 
	 * @return
	 */
	@RequestMapping("/insertCustomized")	
	@ResponseBody
	public Result insertCustomized(HttpServletRequest request,ezs_customized customized){
		Result result = new Result();
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		//加入预约定制
		int n = goodsService.insertCustomized(customized);
		Long id = customized.getId();
		ezs_customized_record record = new ezs_customized_record();
		record.setId(id);
		record.setAddTime(new Date());
		record.setDeleteStatus(false);
		record.setOperater_id(user.getId());
		record.setPurchaser_id(user.getId());
		int m = goodsService.insertCustomizedRecord(record);
		if(n > 0 && m > 0){
			result.setMsg("添加成功");
			result.setSuccess(true);
		}
		if(n > 0 && m <= 0){
			result.setMsg("添加成功，但记录失败");
			result.setSuccess(true);
		}
		if(n > 0 && m <= 0){
			result.setMsg("添加失败，但插入了记录");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 同类货品（以及品类筛选都是走这个方法）
	 * @param id 商品类别id
	 * @return
	 */
	@RequestMapping("/listForGoods")
	@ResponseBody
	public Result listForGoods(Long goodClass_id){
		Result result = new Result();
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		if(null != goodClass_id){
			list = goodsService.listForGoods(goodClass_id);
			result.setObj(list);
			result.setSuccess(true);
			result.setMsg("返回成功");
		}else{
			result.setMsg("返回失败");
			result.setSuccess(false);
		}
		return result;
	}
	
	/**
	 * 商品多条件查询，地区，品类（字符数组），默认排序：添加时间降序，库存量：从大到小，筛选：颜色（字符数组），形态（字符数组），来源，重要参数，是否环保，搜索框输入条件
	 * @param request
	 * @param areaId	地区id
	 * @param typeId	品类id字符数组
	 * @param colorId	颜色id字符数组
	 * @param formId	形态id字符数组
	 * @param source	来源
	 * @param purpose	用途
	 * @param importantParam	重要参数
	 * @param isProtection	是否环保
	 * @param goodsName	搜索框条件：商品名称
	 * @return
	 */
	@RequestMapping("/queryGoodsList")
	@ResponseBody
	public Result queryGoodsList(HttpServletRequest request,
			@RequestParam(name = "areaId",required=true)String areaId,
			@RequestParam(name = "typeId",required=false)String typeId,
			@RequestParam(name = "colorId",required=false)String colorId,
			@RequestParam(name = "formId",required=false)String formId,
			@RequestParam(name = "source",required=false)String source,
			@RequestParam(name = "purpose",required=false)String purpose,
			@RequestParam(name = "importantParam",required=false)String importantParam,
			@RequestParam(name = "isProtection",required=false)String isProtection,
			@RequestParam(name = "goodsName",required=false)String goodsName,
			@RequestParam(name = "pageNow", defaultValue = "1") int pageNow){
		Result result = Result.failure();
		Long area = Long.valueOf(areaId);
		String[] typeIds = null;
		String[] colorIds = null;
		String[] formIds = null;
		if(null != typeId){
			typeIds = typeId.split(",");
		}
		if(null != colorId){
			colorIds = colorId.split(",");
		}
		if(null != formId){
			formIds = formId.split(",");
		}
//		分页先搁这儿
//		Page page = new Page();
//		page.setStartPos(pageNow);
//		page.setPageNow(pageNow);
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		list = goodsService.queryGoodsList(area,typeIds,colorIds,formIds,source,purpose,importantParam,isProtection,goodsName);
		if(null != list && list.size() > 0){
			result.setMsg("查询成功");
			result.setSuccess(true);
			result.setObj(list);
		}
		return result;
	}
	
	/**
	 * 返回重要参数条件列表
	 * @param request
	 * @return
	 */
	public Result parameterList(HttpServletRequest request){
		Result result = Result.failure();
		
				
		
		
		
		return result;
	}
	
	/**
	 * 根据地区名返回id
	 * @param request
	 * @return
	 */
	@RequestMapping("/areaToId")
	@ResponseBody
	public Result areaToId(HttpServletRequest request,String areaName){
		Result result = Result.failure();
		Long id = goodsService.areaToId(areaName);
		if(null != id){
			result.setSuccess(true);
			result.setObj(id);
			result.setMsg("返回的id为："+id);
		}
		return result;
	}
	
	/**
	 * 返回其他筛选所需的条件:颜色 形态
	 * @param request
	 * @return
	 */
	@RequestMapping("/colorAndFormList")
	@ResponseBody
	public Result colorAndFormList(HttpServletRequest request){
		Result result = Result.failure();
		//颜色 
		List<CurrencyClass> colorList = goodsService.colorList();
		//形态
		List<CurrencyClass> formList = goodsService.formList();
		Map<String,List<CurrencyClass>> map = new HashMap<String,List<CurrencyClass>>();
		map.put("color", colorList);
		map.put("form", formList);
		if(colorList.size() > 0 && null == formList){
			result.setMsg("颜色有值，形态为空");
			result.setObj(map);
			result.setSuccess(true);
		}
		if(null == colorList && formList.size()>0){
			result.setMsg("形态有值，颜色为空");
			result.setObj(map);
			result.setSuccess(true);
		}
		if(formList.size()>0 && colorList.size() > 0){
			result.setMsg("颜色形态都有值");
			result.setObj(map);
			result.setSuccess(true);
		}
		return result;
	}
	
	//生成PDF（质检报告）
	/**
	 * 
	 * @param params
	 * @param templPath	模板路径
	 * @param ftlName 文件名称
	 * @param htmlPath	生成的html文件的名称
	 * @param pdfPath	导出pdf的路径
	 * @param fontPath	
	 * @return
	 */
	@RequestMapping("/exportPDF")
	@ResponseBody
	public String exportPDF(Map<String, Object> params, String templPath, String ftlName, String htmlPath,
			String pdfPath, String fontPath){
		Configuration configuration = null;
		try {
			configuration = new Configuration();
			configuration.setDefaultEncoding("UTF-8");
			configuration.setDirectoryForTemplateLoading(new File(templPath));
			Template temp = configuration.getTemplate(ftlName);		//文件名称
			File htmlFile = new File(htmlPath);
			if (!htmlFile.exists()) {
				htmlFile.createNewFile();
			}
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(htmlPath)), "utf-8"));
			temp.process(params, out);
			out.flush();

			String url = htmlFile.toURI().toURL().toString();
			OutputStream os = new FileOutputStream(pdfPath);
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocument(url);
			
			// 解决中文问题
			ITextFontResolver fontResolver = renderer.getFontResolver();
			fontResolver.addFont(fontPath + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

			renderer.layout();
			renderer.createPDF(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return pdfPath; 
		
	}
	
	/**
	 * 上传发票图片，返回url
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/uploadinvoice",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Result uploadImg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Result result=Result.failure();
		try {
			Map<String , Object> map=fileUploadService.uploadFile(request,0,0,10*1024*1024l);
			if("000".equals(map.get("code"))){
				result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				result.setMsg("上传图片成功");
				Map<String, Object> map1=new HashMap<>();
				map1.put("url", map.get("url"));	//返回前台上传的图片路径
				result.setSuccess(true);
				result.setObj(map1);
				return result;
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setMsg("上传图片失败");
				result.setObj("");
				result.setSuccess(false);
				return result;
			}
		} catch (Exception e) {
			log.info("文件：上传接口调用失败"+e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setMsg("上传图片失败");
			result.setObj("");
			result.setSuccess(false);
			return result;
		} 
	}
	
	/**
	 * 插入发票信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertinvoice")
	@ResponseBody
	public Result insertinvoice(HttpServletRequest request,ezs_invoice invoice) {
		Result result = new Result();
		//关于发票图片的处理暂时不确定，所以暂时，暂时，先放这儿
		
		
		return result;
	}
	/**
	 * 添加购物车
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param goodCarList(List<ezs_goodscart>类型的JSON串)
	 * @return
	 */
	@RequestMapping(value="/addToGoodCar",method=RequestMethod.POST)
	@ResponseBody
	public Object addToGoodCar(HttpServletRequest request,HttpServletResponse response,String goodCarList){
		String sessionId = request.getSession().getId();
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if(goodCarList==null||goodCarList.trim().equals("")){
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("请选择商品");
			return rs;
		}
		List<ezs_goodscart> tGoodCarList = (List<ezs_goodscart>)JSONArray.parseArray(goodCarList, ezs_goodscart.class);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		try {
			mmp = this.goodsService.addGoodsCart(tGoodCarList, user,sessionId);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				rs = Result.success();
				rs.setMsg(mmp.get("Msg").toString());
			}else{
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg("数据传递有误");
		}
		return rs;
	}
	/**
	 * 添加购物车
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param goodsId
	 * @param count
	 * @return
	 */
	@RequestMapping(value="/addToSelfGoodCar")
	@ResponseBody
	public Object addToSelfGoodCar(HttpServletRequest request,HttpServletResponse response,Long goodsId,Double count){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		ezs_goodscart goodsCart = new ezs_goodscart();
		goodsCart.setCount(count);
		goodsCart.setGoods_id(goodsId);
		try {
			mmp = this.goodsService.addGoodsCartFunc(goodsCart, user);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				rs = Result.success();
				rs.setMsg(mmp.get("Msg").toString());
			}else{
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg("数据传递有误");
		}
		return rs;
	}
	/**
	 * 直接下订单（添加订单）
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param orderForm(ezs_orderform类型的JSON串)
	 * @return
	 */
	@RequestMapping("/addToOrderForm")
	@ResponseBody
	public Object directAddToOrderForm(HttpServletRequest request,HttpServletResponse response,String orderForm){
		String sessionId = request.getSession().getId();
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		try {
			
			JSONObject jsonObject = JSONObject.fromObject(orderForm);
			ezs_orderform tOrderForm = (ezs_orderform)JSONObject.toBean(jsonObject, ezs_orderform.class);
			
			mmp = this.goodsService.addOrderForm(tOrderForm, user,sessionId);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				rs = Result.success();
				rs.setMsg(mmp.get("Msg").toString());
			}else{
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg("数据传递有误");
		}
		return rs;
	}
	/**
	 * 直接下订单（添加订单）
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param orderForm(ezs_orderform类型的JSON串)
	 * @return
	 */
	@RequestMapping("/addToSelfOrderForm")
	@ResponseBody
	public Object directAddToSelfOrderForm(HttpServletRequest request,HttpServletResponse response,String orderForm){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		try {
			JSONObject jsonObject = JSONObject.fromObject(orderForm);
			ezs_orderform tOrderForm = (ezs_orderform)JSONObject.toBean(jsonObject, ezs_orderform.class);
			mmp = this.goodsService.addOrderFormFunc(tOrderForm, user);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				rs = Result.success();
				rs.setMsg(mmp.get("Msg").toString());
			}else{
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg("数据传递有误");
		}
		return rs;
	}
	/**
	 * 获取购物车数据
	 * @author zhaibin 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getGoodCar")
	@ResponseBody
	public Object getGoodCar(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		try {
			mmp = this.goodsService.getGoodCarFunc(user);
			Integer ErrorCode = (Integer) mmp.get("ErrorCode");
			if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
				List<ezs_goodscart> goodCarList = (List<ezs_goodscart>) mmp.get("Obj");
				rs = Result.success();
				rs.setObj(goodCarList);
				rs.setMsg(mmp.get("Msg").toString());
			}else{
				rs = Result.failure();
				rs.setMsg(mmp.get("Msg").toString());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			rs = Result.failure();
			rs.setMsg(mmp.get("Msg").toString());
		}
		return rs;
	}
}
