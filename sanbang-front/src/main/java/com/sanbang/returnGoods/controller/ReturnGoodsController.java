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
	
}
