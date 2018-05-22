package com.sanbang.buyer.service;

import java.util.Map;

public interface GoodsInvoiceService {
	
	public Map<String, Object> getInvoiceByUser(Long userId);
	
	public Map<String, Object> getInvoiceByKey(Long id);
	
	public Map<String, Object> changeInvoiceStateById(Long id);

}
