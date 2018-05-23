package com.sanbang.goods.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.pdf.BaseFont;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;


import freemarker.template.Configuration;
import freemarker.template.Template;


@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;

	/**
	 * 查询货品详情（描述说明也走这方法）
	 * @param request
	 * @param id 货品id
	 * @return
	 */
	@RequestMapping("/goodsDetail")
	@ResponseBody
	public Result getGoodsDetail(HttpServletRequest request,Long id){
		ezs_goods goods = goodsService.getGoodsDetail(id);
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		result.setObj(goods);
		return   result;
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
		List<ezs_dvaluate> list  = new ArrayList();
		list = goodsService.listForEvaluate(id);
		result.setObj(list);
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
		int n;
		if(goodId != null){
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
					goodsService.insertCollect(goodId);
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
	@RequestMapping("/insertCart")	//sql
	@ResponseBody
	public Result insertCart(HttpServletRequest request,ezs_goodscart goodsCart){
		Result result = new Result();
		int n;
		n = goodsService.insertCart(goodsCart);
		if(n>0){
			result.setObj(goodsCart);
			result.setMsg("添加成功");
		}
		return result;
	}
	
	/**
	 * 立即购买（加入订单）
	 * @param request
	 * @param order
	 * @return
	 */
	@RequestMapping("/insertOrder")		//sql错误
	@ResponseBody
	public Result insertOrder(HttpServletRequest request,ezs_orderform order){
		Result result = new Result();
		int n;
		n = goodsService.insertOrder(order);
		if(n>0){
			result.setMsg("添加成功");
		}
		return result;
	}
	
	/**
	 * 预约预定
	 * @param request
	 * @param goodsCart	
	 * @return
	 */
	@RequestMapping("/insertReserveOrder")	
	@ResponseBody
	public Result insertReserveOrder(HttpServletRequest request,ezs_goodscart goodsCart){
		Result result = new Result();
		
		
		
		return result;
	}
	
	/**
	 * 同类货品（以及品类筛选都是走这个方法）
	 * @param id 商品类别id
	 * @return
	 */
	@RequestMapping("/listForGoods")		//sql错误
	@ResponseBody
	public Result listForGoods(Long goodClass_id){
		Result result = new Result();
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		if(null != goodClass_id){
			list = goodsService.listForGoods(goodClass_id);
			result.setObj(list);
			result.setMsg("返回成功");
		}else{
			result.setMsg("返回失败");
		}
		return result;
	}
	
	
	/**
	 * 自营、地区筛选、品类筛选
	 * @param request
	 * @param area	地区id
	 * @param type	类别id
	 * @return
	 */
	@RequestMapping("/areaAndType")
	@ResponseBody
	public Result listByAreaAndType(HttpServletRequest request,Long area,Long type){
		Result result=Result.success();
		List<ezs_goods> list = new ArrayList();
		list = goodsService.listByAreaAndType(area,type);
		result.setObj(list);
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
		List<ezs_goods> list = new ArrayList();
		//将接收的参数转换成map类型
		Map mmp = new HashMap();
		mmp.put("color", color);
		mmp.put("form", form);
		mmp.put("purpose", purpose);
		mmp.put("source", source);
		mmp.put("burning", burning);
		mmp.put("protection", protection);
		
		list = goodsService.listByOthers(mmp);
		result.setObj(list);
		result.setMsg("返回成功");
		
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
}
