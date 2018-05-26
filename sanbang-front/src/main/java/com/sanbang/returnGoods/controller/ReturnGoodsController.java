package com.sanbang.returnGoods.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.returnGoods.service.ReturnGoodsService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;

@Controller
@RequestMapping("/returnGoods")
public class ReturnGoodsController {
	
	@Autowired
	private ReturnGoodsService returnGoodsService;
	
	
	/**
	 * 卖家所有退货订单
	 * @param request
	 * @return
	 */
	@RequestMapping("/returnOrder")
	@ResponseBody
	public Result returnOrder(HttpServletRequest request){
		List list = returnGoodsService.returnOrder();
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		
		
		
		
		result.setObj(list);
		return   result;
	}
	
	
	
	
	/**
	 * 退货订单条件查询
	 * @param request
	 * @param orderType	订单类型
	 * @param returnState	退货状态
	 * @return
	 */
	@RequestMapping("/queruyOrder")
	@ResponseBody
	public Result queruyOrderByTypeAndState(HttpServletRequest request,Long orderType,Long returnState){
		List list = returnGoodsService.returnOrder();
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		
		
		
		
		result.setObj(list);
		return   result;
	}
	
	
	/**
	 * 物流详情（分样品和货品，但是只看id就可以）
	 * @param request
	 * @param orderId 订单信息
	 * @return
	 */
	@RequestMapping("/logisticsDetail")
	@ResponseBody
	public Result logisticsDetail(HttpServletRequest request,Long orderId){
		List list = returnGoodsService.returnOrder();
		Result result=Result.failure();
		result.setMeta(new Page(1, 1, 1,1, 1, false, false, false, false));
		
		
		
		
		result.setObj(list);
		return   result;
	}
	
}
