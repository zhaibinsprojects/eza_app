package com.sanbang.advice.service;

import com.sanbang.utils.Result;

public interface CommonOrderAdvice {
	
	//订单状态通知
	public  Result orderFormAdvice(String order_no,String order_status);
	
	//退货订单状态通知
	 public  Result returnOrderAdvice(long returnOrderid);
}
