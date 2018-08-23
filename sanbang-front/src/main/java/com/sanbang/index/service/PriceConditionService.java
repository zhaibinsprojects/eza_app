package com.sanbang.index.service;

import java.util.Map;

import com.sanbang.utils.Page;

public interface PriceConditionService {
	
	public Map<String, Object> getPriceInTime(Map<String, Object> mp,int pageno,int pagesaize);
	/*实时报价-再生料-列表-多条件查询*/
	public Map<String, Object> getPriceInTimeOld(Map<String, Object> mp,int pageno,int pagesaize);
	/*实时报价-新料-列表-多条件查询*/
	public Map<String, Object> getPriceInTimeNew(Map<String, Object> mp,int pageno,int pagesaize);
	
	public Map<String, Object> getSecondTheme(Long id);
	
	public Map<String, Object> getPriceTrendcy(Map<String, Object> mp,int currentPage,int pagesaize); 
	/*实时报价-新*/
	public Map<String, Object> priceInTimeNew(Long goodClassId);
	/*价格趋势-新-再生料*/
	public Map<String, Object> getPriceTrendcyNew(Map<String, Object> mp,int currentPage,int pagesaize);
	/*实时报价-新-新料*/
	public Map<String, Object> priceInTimeNew2(Long goodClassId);
	
	/*实时报价-新料-详情页面*/
	public Map<String, Object> priceInTimeNewDetail(Map<String, Object> mp);
	/*实时报价-新料-详情页面-分页展示*/
	public Map<String, Object> priceInTimeNewDetailPage(Map<String, Object> mp,int currentPage,int pagesaize);
	/*实时报价-再生料-详情页面*/
	public Map<String, Object> priceInTimeOldDetail(Map<String, Object> mp);
	/*实时报价-再生料-详情页面-分页展示*/
	public Map<String, Object> priceInTimeOldDetailPage(Map<String, Object> mp,int currentPage,int pagesaize);
}
