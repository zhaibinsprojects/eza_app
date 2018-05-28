package com.sanbang.seller.service;


import javax.servlet.http.HttpServletRequest;

import com.sanbang.utils.Result;

public interface SellerReturnOrderService {

	/**
	 * 退货订单列表
	 * @param pageNo
	 * @return
	 */
	public  Result returnOrderList(int pageNo,long userid,int order_type,int state2);
	 /**
     * 退货订单详情
     * @param returnid
     * @return
     */
	Result returnOrderinfoforBuyer(long returnid);
	
	/**
	 * 退货物流
	 * @param returnid
	 * @return
	 */
	Result getreturnlogistics(long returnid);
	
	/**
	 * 提交线下退款凭证
	 * @param request
	 * @param params
	 * @return
	 */
	Result  submitReturnPayInfo(HttpServletRequest request,String params,long returnid);
	
}
