package com.sanbang.advice.service;

import com.sanbang.utils.Result;

public interface CommonOrderAdvice {
	
	//订单状态通知
	public  Result orderFormAdviceStatus(String order_no,String order_status);
	
	//订单详情通知
	 public  Result returnOrderAdvice(String order_no,String order_status);
}
