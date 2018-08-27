package com.sanbang.vo.hangq;

import java.util.List;
import java.util.Map;

public class HangqAreaData {
	
	private  long id;
	private String AreaName;
	private List<Map<String, Object>> children;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAreaName() {
		return AreaName;
	}
	public void setAreaName(String areaName) {
		AreaName = areaName;
	}
	public List<Map<String, Object>> getChildren() {
		return children;
	}
	public void setChildren(List<Map<String, Object>> children) {
		this.children = children;
	}
	
	
	
	
}
