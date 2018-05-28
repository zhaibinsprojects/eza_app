package com.sanbang.buyer.service;

import com.sanbang.utils.Result;

public interface BuyerReturnOrderService {

	/**
	 * 退货订单列表
	 * @param pageNo
	 * @return
	 */
	public  Result returnOrderList(int pageNo,long userid);
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
	
}
