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
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;


@Controller
@RequestMapping("/goods")
public class GoodsController {
	
	@Autowired
	private GoodsService goodsService;

	/**
	 * 查询货品详情
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
	public Result listForEvaluate(HttpServletRequest request,long id){
		Result result = new Result();
		List<ezs_dvaluate> list  = new ArrayList();
		list = goodsService.listForEvaluate(id);
		result.setObj(list);
		return result;
	}
	
	//货品收藏（）
	
	
	/**
	 * 加入采购单（加入购物车）
	 * @param request
	 * @param goodsCart
	 * @return
	 */
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
	
	
	//立即购买（加入订单）
	public Result insertOrder(HttpServletRequest request,ezs_goodscart goodsCart){
		Result result = new Result();
		
		
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	//预约预定
	public Result insertReserveOrder(HttpServletRequest request,ezs_goodscart goodsCart){
		Result result = new Result();
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
}
