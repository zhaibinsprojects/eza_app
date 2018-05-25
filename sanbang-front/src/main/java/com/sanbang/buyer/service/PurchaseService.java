package com.sanbang.buyer.service;

import java.util.Map;

public interface PurchaseService {
	
	public Map<String, Object> getPurchaseGoodsByUser(Long userId);
	
	public Map<String, Object> getPurchaseById(Long Id);
	
	public Map<String, Object> submitOrders();
	
	public Map<String, Object> submitQuestion(String Msg); 

}
