package com.sanbang.app.controller;

import java.awt.Color;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
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
import com.sanbang.vo.goods.GoodsVo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app/goods")
public class AppGoodsController {
	
	@Autowired
	private GoodsService goodsService;
	
	@Resource(name="fileUploadService")
	private FileUploadService fileUploadService;
	// 日志
	private static Logger log = Logger.getLogger(FileUploadServiceImpl.class);
	private static final String view="/goods/";
	
	
	
	/**
	 * @author langjf
	 * forapp 
	 * 查询货品详情
	 * @param request
	 * @param id 货品id
	 * @return
	 */
	@RequestMapping("/toGoodsShow")
	public String toGoodsShow(HttpServletRequest request,Long id,Model model){
		GoodsVo  goodsvo=goodsService.getgoodsinfo(id);
		model.addAttribute("good", goodsvo);
		return view+"goodsshow";
	}
	
	
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
					result.setMsg("收藏成功");
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
	 * @param goodsCart
	 * @return
	 */
	@RequestMapping("/insertCart")
	@ResponseBody
	public Result insertCart(HttpServletRequest request,ezs_goodscart goodsCart){
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		ezs_bill bill = user.getEzs_bill();
		if(null != goodsCart){
			goodsCart.setBill(bill);
		}
		Result result = new Result();
		int n;
		n = goodsService.insertCart(goodsCart);
		if(n>0){
			result.setObj(goodsCart);
			result.setSuccess(true);
			result.setMsg("添加成功");
		}else{
			result.setSuccess(false);
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
	public Result insertOrder(HttpServletRequest request,ezs_orderform order){
		Result result = Result.failure();
		int n;
		n = goodsService.insertOrder(order);
		if(n>0){
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
	 * 多条件查询
	 * @param request
	 * @param areaId	地区id
	 * @param typeId	品类id字符串数组
	 * @param addTime	默认
	 * @param inventory	库存
	 * @param colorId	颜色id字符串数组
	 * @param formId	形态id字符串数组
	 * @param source	来源
	 * @param purpose	用途
	 * @param density	密度字符串数组
	 * @param cantilever	悬臂梁缺口冲击字符串数组
	 * @param freely	简支梁缺口冲击字符串数组
	 * @param lipolysis	熔融指数（溶脂）字符串数组
	 * @param ash	灰分字符串数组
	 * @param water	水分字符串数组
	 * @param tensile	拉伸强度字符串数组
	 * @param crack	断裂伸长率字符串数组
	 * @param bending	弯曲强度字符串数组
	 * @param flexural	弯曲模量字符串数组
	 * @param isProtection	是否环保
	 * @param goodsName	搜索框条件：商品名称
	 * @return
	 */
	@RequestMapping("/queryGoodsList")
	@ResponseBody
	public Result queryGoodsList(HttpServletRequest request,
			@RequestParam(name = "areaId",required=true)String areaId,
			@RequestParam(name = "typeId",required=false)String typeId,
			@RequestParam(name = "defaultId",required=false)String defaultId,	//默认值（添加时间）
			@RequestParam(name = "inventory",required=false)String inventory,	//库存量
			@RequestParam(name = "colorId",required=false)String colorId,
			@RequestParam(name = "formId",required=false)String formId,
			@RequestParam(name = "source",required=false)String source,
			@RequestParam(name = "purpose",required=false)String purpose,
			@RequestParam(name = "price",required=false)String price,	//价格区间
			@RequestParam(name = "density",required=false)String density,	//密度
			@RequestParam(name = "cantilever",required=false)String cantilever,	//悬臂梁缺口冲击
			@RequestParam(name = "freely",required=false)String freely,	//简支梁缺口冲击
			@RequestParam(name = "lipolysis",required=false)String lipolysis,	//熔融指数（溶脂）
			@RequestParam(name = "ash",required=false)String ash,	//灰分
			@RequestParam(name = "water",required=false)String water,	//水分
			@RequestParam(name = "tensile",required=false)String tensile,	//拉伸强度
			@RequestParam(name = "crack",required=false)String crack,	//断裂伸长率
			@RequestParam(name = "bending",required=false)String bending,	//弯曲强度
			@RequestParam(name = "flexural",required=false)String flexural,	//弯曲模量
			@RequestParam(name = "burning",required=false)String burning,	//燃烧等级
			@RequestParam(name = "isProtection",required=false)String isProtection,
			@RequestParam(name = "goodsName",required=false)String goodsName,
			@RequestParam(name = "pageNow", defaultValue = "1") int pageNow){
		Result result = Result.failure();
		Long area = Long.valueOf(areaId);
		List<Long> areaList = new ArrayList<Long>();
		//前端传过来的可能是单个的id值也可能为空，如果为空则是所有，目前地区分为三级
		//传过来的id是省就查询该省份下的，是市就查询该市下的，是县或者区就查区的
		List<Long> listId = goodsService.queryChildId(area);	//查询当前id（省）下的所有子id（市），或查当前市下的所有区县
		if(null != listId && listId.size() != 0){
			List<Long> listIds = goodsService.queryChildIds(listId);  //查询省下的所有市的所有区县
			if(null != listIds && listIds.size() != 0){   //area是省
				areaList = listIds;
			}else{	//area是市
				areaList = listId;
			}
		}else{	//area是县、区
			areaList.add(area);
		}
		String[] typeIds = null;
		String[] colorIds = null;
		String[] formIds = null;
		String[] prices = null;
		//重要参数
		String[] densitys = null;
		String[] cantilevers = null;
		String[] freelys = null;
		String[] lipolysises = null;
		String[] ashs = null;
		String[] waters = null;
		String[] tensiles = null;
		String[] cracks = null;
		String[] bendings = null;
		String[] flexurals = null;
		String[] burnings = null;
		if(null != typeId && !"".equals(typeId)){
			typeIds = typeId.split(",");
		}
		if(null != colorId && !"".equals(colorId)){
			colorIds = colorId.split(",");
		}
		if(null != formId && !"".equals(formId)){
			formIds = formId.split(",");
		}
		if(null != price && !"".equals(price)){
			prices = price.split(",");
		}
		//重要参数
		if(null != density  && !"".equals(density)){
			densitys = density.split(",");
		}
		if(null != cantilever && !"".equals(cantilever)){
			cantilevers = cantilever.split(",");
		}
		if(null != freely && !"".equals(freely)){
			freelys = freely.split(",");
		}
		if(null != lipolysis && !"".equals(lipolysis)){
			lipolysises = lipolysis.split(",");
		}
		if(null != ash && !"".equals(ash)){
			ashs = ash.split(",");
		}
		if(null != water && !"".equals(water)){
			waters = water.split(",");
		}
		if(null != tensile && !"".equals(tensile)){
			tensiles = tensile.split(",");
		}
		if(null != crack && !"".equals(crack)){
			cracks = crack.split(",");
		}
		if(null != bending && !"".equals(bending)){
			bendings = bending.split(",");
		}
		if(null != flexural && !"".equals(flexural)){
			flexurals = flexural.split(",");
		}
		if(null != burning && !"".equals(burning)){
			burnings = burning.split(",");
		}
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		int pageStart = (pageNow - 1) * 10;	//起始页，每页10条
		list = goodsService.queryGoodsList(areaList,typeIds,defaultId,inventory,colorIds,formIds,source,purpose,prices,densitys,cantilevers,freelys,
				lipolysises,ashs,waters,tensiles,cracks,bendings,flexurals,burnings,isProtection,goodsName,pageStart);
		if(null != list && list.size() > 0){
			result.setSuccess(true);
			result.setMsg("筛选成功");
			result.setObj(list);
			Page page = new Page(list.size(), pageNow);
			result.setMeta(page);
		}else{
			result.setSuccess(false);
			result.setMsg("数据为空");
			result.setObj(list);
		}
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
		if(null != areaName && !"".equals(areaName)){
			List<Long> ids = goodsService.areaToId(areaName);
			//直辖市
			if(areaName.contains("北京")||areaName.contains("上海")||areaName.contains("天津")||areaName.contains("重庆")){
				if(null != ids && ids.size()==2){
					Long id1 = ids.get(0);
					Long id2 = ids.get(1);
					if(id1<id2){
						result.setObj(id2);
					}else{
						result.setObj(id1);
					}
				}
				result.setSuccess(true);
				result.setMsg("返回成功");
			}else if(null != ids && ids.size() != 0){
				result.setObj(ids.get(0));
				result.setSuccess(true);
				result.setMsg("返回成功");
			}else{
				result.setObj("");
				result.setSuccess(false);
				result.setMsg("查询为空");
			}
		}else{
			result.setMsg("参数为空,请传入正确参数");
			result.setSuccess(false);
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
		HashMap<String, Object> map1 = new HashMap<String,Object>();
		HashMap<String,Object> map2 = new HashMap<String,Object>();
		map1.put("second", colorList);
		map1.put("type", "颜色");
		map2.put("second", formList);
		map2.put("type", "形态");
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		list.add(map1);
		list.add(map2);
		if(colorList.size() > 0 && null == formList){
			result.setMsg("颜色有值，形态为空");
			result.setObj(list);
			result.setSuccess(true);
		}
		if(null == colorList && formList.size()>0){
			result.setMsg("形态有值，颜色为空");
			result.setObj(list);
			result.setSuccess(true);
		}
		if(formList.size()>0 && colorList.size() > 0){
			result.setMsg("颜色形态都有值");
			result.setObj(list);
			result.setSuccess(true);
		}
		return result;
	}
	
	//生成PDF（质检报告）
	/**
	 * 
	 * @param params
	 * @param templPath	模板路径
	 * @param ftlName 模板文件名称
	 * @param htmlPath	生成的html文件的名称
	 * @param pdfPath	导出pdf的路径
	 * @param fontPath	
	 * @return
	 */
	@RequestMapping("/exportPDF")
	@ResponseBody
	public static String exportPDF(Map<String, Object> params, String templPath, String ftlName, String htmlPath,
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

			String url = htmlFile.toURI().toString();	//.toURL()
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
	
//	public static void main(String[] args) {
//		Map<String, Object> map=new HashMap<>();
//		map.put("orderAmount", "aaa");
//		map.put("AcapAmount", "bb");
//		map.put("customerName", "呵呵哒呵呵哒");
//		exportPDF(map, "d:/", "jybtz.ftl", "d:/", "d:/", "d:/fonts");
//	}
	
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
	 * 样品下订单
	 * @author zhaibin
	 * @param request
	 * @param response
	 * @param orderForm(ezs_orderform类型的JSON串)
	 * @return
	 */
	@RequestMapping("/addToSelfSampleOrderForm")
	@ResponseBody
	public Object addToSampleOrderForm(HttpServletRequest request,HttpServletResponse response,String orderForm){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		try {
			JSONObject jsonObject = JSONObject.fromObject(orderForm);
			ezs_orderform tOrderForm = (ezs_orderform)JSONObject.toBean(jsonObject, ezs_orderform.class);
			mmp = this.goodsService.addOrderFormFunc(tOrderForm, user, "SAMPLE" );
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
		ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
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
	@RequestMapping("/addToSelfOrderForm")
	@ResponseBody
	public Object directAddToSelfOrderForm(HttpServletRequest request,HttpServletResponse response,String orderForm){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
		if (user == null) {
			rs = Result.failure();
			rs.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			rs.setMsg("用户未登录");
			return rs;
		}
		try {
			JSONObject jsonObject = JSONObject.fromObject(orderForm);
			ezs_orderform tOrderForm = (ezs_orderform)JSONObject.toBean(jsonObject, ezs_orderform.class);
			mmp = this.goodsService.addOrderFormFunc(tOrderForm, user, "GOODS" );
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/getGoodCar")
	@ResponseBody
	public Object getGoodCar(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> mmp = null;
		Result rs = null;
		ezs_user user = RedisUserSession.getUserInfoByKeyForApp(request);
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
