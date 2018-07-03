package com.sanbang.buyer.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface PurchaseService {
	
	public Map<String, Object> getPurchaseGoodsByUser(HttpServletRequest request,Long userId);
	
	public Map<String, Object> getPurchaseById(Long Id);
	
	public Map<String, Object> submitOrders();
	
	public Map<String, Object> submitQuestion(String Msg); 

}
