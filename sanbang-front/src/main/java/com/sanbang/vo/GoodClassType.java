package com.sanbang.vo;

import java.util.Date;
import java.util.List;

public class GoodClassType {
	//商品类别：新料、再生类
	private Long classId;
	
	private String className;
	
	private Date date;
	//价格信息列表
	private List<PriceInTimesVo> priceInTimeList;

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<PriceInTimesVo> getPriceInTimeList() {
		return priceInTimeList;
	}

	public void setPriceInTimeList(List<PriceInTimesVo> priceInTimeList) {
		this.priceInTimeList = priceInTimeList;
	}

}
