package com.sanbang.index.service;

import java.util.Map;

import com.sanbang.utils.Page;

public interface PriceConditionService {
	
	public Map<String, Object> getPriceInTime(Map<String, Object> mp,int pageno,int pagesaize);
	
	public Map<String, Object> getSecondTheme(Long id);
	
	public Map<String, Object> getPriceTrendcy(Map<String, Object> mp,int currentPage,int pagesaize); 
	/*实时报价-新*/
	public Map<String, Object> priceInTimeNew(Long goodClassId);
	/*价格趋势-新*/
	public Map<String, Object> getPriceTrendcyNew(Map<String, Object> mp,int currentPage,int pagesaize);
}
