package com.sanbang.vo;

import java.math.BigDecimal;
import java.util.List;

public class Series {
	public String name;  
	
    public String type;  
    
    public List<BigDecimal> data;
    
    public List<String> dateList;
    
	public List<String> getDateList() {
		return dateList;
	}
	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<BigDecimal> getData() {
		return data;
	}
	public void setData(List<BigDecimal> data) {
		this.data = data;
	}
}
