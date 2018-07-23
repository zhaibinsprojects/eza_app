package com.sanbang.index.service;

import java.util.Map;

public interface IndustryInfoService {
	
	public Map<String, Object> getSecondTheme(Long id);
	
	public Map<String, Object> getIndustryInfoByKinds(Long kindsId,int currentPage);
	
	public Map<String, Object> getAllIndustryInfoByParentKinds(Long parentKindsId,int currentPage,int pagesize);
	/**
	 * 根据二级栏目获取文章name和meta(不要文章内容，影响效率)
	 * @param parentKindsId
	 * @param currentPage
	 * @return
	 */
	public Map<String, Object> getEssayBySecondTheme(Long parentKindsId,String currentPage);
}
