package com.sanbang.seller.service;

import java.util.List;
import java.util.Map;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.vo.PagerOrder;

public interface SellerOrderService {

	Map<String, Object> queryOrderInfoById(String order_no);

	ezs_logistics queryLogisticsByNo(String orderNo);

	List<ezs_order_info> getOrderListByValue(PagerOrder pager);

}

