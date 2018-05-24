package com.sanbang.index.service;

import java.util.Map;

public interface IndustryInfoService {
	
	public Map<String, Object> getSecondTheme(Long id);
	
	public Map<String, Object> getIndustryInfoByKinds(Long kindsId,String currentPage);
	
	public Map<String, Object> getAllIndustryInfoByParentKinds(Long parentKindsId,String currentPage);
}
