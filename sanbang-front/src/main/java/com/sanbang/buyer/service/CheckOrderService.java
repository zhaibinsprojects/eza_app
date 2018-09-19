package com.sanbang.buyer.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.bean.ezs_check_order_items;
import com.sanbang.bean.ezs_check_order_main;
import com.sanbang.utils.Result;

public interface CheckOrderService {

	/**
	 * 对账单查看
	 * @param request
	 * @param result
	 * @param orderNo
	 * @return
	 */
	public Result getCheckOrderH(HttpServletRequest request,Result result,String orderno);
	
	/***
	 * 
	 * @param request
	 * @param result
	 * @param orderno
	 * @param items
	 * @return
	 */
	public Result addOrUpdateCheckOrder(HttpServletRequest request,Result result,String orderno,List<ezs_check_order_items> items,ezs_check_order_main main);
	
}
