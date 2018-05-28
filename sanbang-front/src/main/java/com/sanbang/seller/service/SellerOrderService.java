package com.sanbang.seller.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.utils.Result;
import com.sanbang.vo.PagerOrder;

public interface SellerOrderService {

	Map<String, Object> queryOrderInfoById(String order_no);

	ezs_logistics queryLogisticsByNo(String orderNo);

	List<ezs_order_info> getOrderListByValue(PagerOrder pager);

	ezs_invoice queryInvoiceByNo(String orderNo);

	Result seller_order_signature(String order_no, HttpServletRequest request, HttpServletResponse response);

	Result sampleDelivery(Result result, String order_no, HttpServletRequest request, HttpServletResponse response);

	Result goodsDelivery(Result result, String order_no, HttpServletRequest request, HttpServletResponse response);
}

