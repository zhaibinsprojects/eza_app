package com.sanbang.index.service;

import java.util.Map;

import com.sanbang.vo.ExPage;

public interface ReportEssayServer {
	
	public Map<String, Object> getSecondTheme(Long parentId);
	
	public Map<String, Object> getReportEssayTheme(Long parentId,int currentPage);
	
	public Map<String, Object> getReportEssayContext(Long reportId);

}
