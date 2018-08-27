package com.sanbang.vo.hangq;

import java.util.List;
import java.util.Map;

public class CataData {
	private  long id;
	private String name;
	private List<Map<String, Object>> children;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, Object>> getChildren() {
		return children;
	}

	public void setChildren(List<Map<String, Object>> children) {
		this.children = children;
	}
	
}
