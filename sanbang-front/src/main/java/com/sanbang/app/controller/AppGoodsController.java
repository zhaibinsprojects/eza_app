package com.sanbang.app.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;
import com.sanbang.bean.ezs_bill;
import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_dict;
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
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/app/goods")
public class AppGoodsController {
	
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
	 * 自营、地区筛选、品类筛选
	 * @param request
	 * @param area	地区id
	 * @param type	类别id
	 * 
	 * @return
	 */
	@RequestMapping("/areaAndType")
	@ResponseBody
	public Result listByAreaAndType(HttpServletRequest request,Long area,Long type){
		Result result=Result.success();
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		//list = goodsService.listByAreaAndType(area,type);
		if(null != list && list.size()>0){
			result.setObj(list);
			result.setSuccess(true);
			result.setMsg("筛选成功");
		}else{
			result.setSuccess(false);
			result.setMsg("筛选失败");
		}
		return result;
	}
	
	/**
	 * 其他筛选
	 * @param request
	 * @param color	颜色
	 * @param form 形状
	 * @param purpose 用途
	 * @param source 来源
	 * @param burning 燃烧等级
	 * @param protection 是否环保
	 * @return
	 */
	@RequestMapping("/others")
	@ResponseBody
	public Result listByOthers(HttpServletRequest request,Long color,Long form,String purpose,String source,String burning,String protection){
		Result result=Result.success();
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		//将接收的参数转换成map类型
		Map<String, Object> mmp = new HashMap<String, Object>();
		mmp.put("color", color);
		mmp.put("form", form);
		mmp.put("purpose", purpose);
		mmp.put("source", source);
		mmp.put("burning", burning);
		mmp.put("protection", protection);
		
		//list = goodsService.listByOthers(mmp);
		result.setObj(list);
		result.setMsg("返回成功");
		
		return result;
	}
	
	/**
	 * 返回其他筛选所需的条件
	 * @param request
	 * @return
	 */
	@RequestMapping("/conditionList")
	@ResponseBody
	public Result conditionList(HttpServletRequest request){
		Result result = new Result();
		List<ezs_dict> list = new ArrayList<ezs_dict>();
		//list = goodsService.conditionList();
		if(list.size()>0){
			result.setObj(list);
			result.setMsg("返回查询条件");
			result.setSuccess(true);
		}else{
			result.setMsg("查询失败");
			result.setSuccess(false);
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
	
//	public static void main(String[] args) {
//		GoodsController  aa=new GoodsController();
//		Map<String, Object> map=new HashMap<>();
//		map.put("orderAmount", "aaa");
//		map.put("AcapAmount", "bb");
//		aa.exportPDF(map, "d:/", "jybtz.ftl", "d:/", "d:/", "d:/fonts");
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
}
