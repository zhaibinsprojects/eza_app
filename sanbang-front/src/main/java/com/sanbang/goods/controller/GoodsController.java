package com.sanbang.goods.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;


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
	public Result getGoodsDetail(HttpServletRequest request,long id){
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
	public Result listForEvaluate(HttpServletRequest request,long id){
		Result result = new Result();
		List<ezs_dvaluate> list  = new ArrayList();
		list = goodsService.listForEvaluate(id);
		result.setObj(list);
		return result;
	}
	
	/**
	 * 当第一次收藏时，是insert,取消收藏时是update更新状态（货品收藏）
	 * @param request
	 * @param share	
	 * @return
	 */
	@RequestMapping("/updateShare")
	@ResponseBody
	public Result updateShare(HttpServletRequest request,Long goodId){
		Result result = new Result();
		int n;
		if(goodId != null){
			goodsService.updateCollect(goodId);
			result.setMsg("取消成功");
		}else{
			n = goodsService.insertCollect(goodId);
			if(n>0){
				result.setMsg("添加成功");
			}
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
	@RequestMapping("/insertOrder")
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
	
	//预约预定
	/**
	 * 
	 * @param request
	 * @param goodsCart	
	 * @return
	 */
	public Result insertReserveOrder(HttpServletRequest request,ezs_goodscart goodsCart){
		Result result = new Result();
		
		
		return result;
	}
	
	/**
	 * 同类货品（以及品类筛选都是走这个方法）
	 * @param id 
	 * @return
	 */
	@RequestMapping("/listForGoods")
	@ResponseBody
	public Result listForGoods(Long id){
		Result result = new Result();
		List<ezs_goods> list = new ArrayList();
		list = goodsService.listForGoods(id);
		result.setObj(list);
		result.setMsg("返回成功");
		return result;
	}
	
	
	/**
	 * 自营、地区筛选、品类筛选
	 * @param request
	 * @param area	地区
	 * @param type	类别
	 * @return
	 */
	@RequestMapping("/areaAndType")
	@ResponseBody
	public Result listByAreaAndType(HttpServletRequest request,String area,String type){
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
	public Result listByOthers(HttpServletRequest request,Long color,Long form,String purpose,String source,String burning,boolean protection){
		Result result=Result.success();
		List<ezs_goods> list = new ArrayList();	
		list = goodsService.listByOthers(color,form,purpose,source,burning,protection);
		result.setObj(list);
		result.setMsg("返回成功");
		
		return result;
	}
	
	
	
}
