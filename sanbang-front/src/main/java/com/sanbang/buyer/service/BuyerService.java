package com.sanbang.buyer.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_order_info;
import com.sanbang.utils.Result;
import com.sanbang.vo.PagerOrder;

public interface BuyerService {

	/**
	 * 
	 * @param meta
	 * @return
	 */
	List<ezs_order_info> getOrderListByValue(PagerOrder pager);
	
	/**
	 * 订单详情
	 * @param orderid
	 * @return
	 */
	Map<String, Object> getOrderInfoShow(String order_no);
	
	/**
	 * 支付确认
	 * @param orderid
	 * @return
	 */
	Map<String, Object>orderconfirm(String order_no);
	
	
	/**
	 * 合同查看
	 * @param request
	 * @param order_no
	 * @return
	 */
	Result showOrderContent(HttpServletRequest request,String order_no);
	
	/**
	 * 签订合同
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	 public Result seller_order_signature(String order_no,HttpServletRequest request,
	            HttpServletResponse response);
	
	 
	 
}