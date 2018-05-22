package com.sanbang.seller.service;

import java.util.Map;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_purchase_orderform;

public interface SellerOrderService {

	Map<String, Object> queryOrders(String currentPage, Long userId, String orderType, Integer orderStatus);

	ezs_purchase_orderform queryOrderInfoById(long orderId);

	ezs_invoice queryInvoiceByNo(String orderNo);

	ezs_logistics queryLogisticsByNo(String orderNo);

}

