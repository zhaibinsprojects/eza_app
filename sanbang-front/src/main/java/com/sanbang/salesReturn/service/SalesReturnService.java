package com.sanbang.salesReturn.service;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.bean.ezs_order_info;
import com.sanbang.bean.ezs_set_return_order;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface SalesReturnService {

	ezs_order_info getOrderListByOrderno(String order_no,ezs_user upi);

	Result insertSetReturnOrder(HttpServletRequest request,ezs_set_return_order returnOrder,ezs_user upi);

}
