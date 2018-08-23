package com.sanbang.vo;

import java.util.List;

public class PriceInTimesVo {
	
	private String goodClassName;
	
	private String goodClassId;
	
	private List<PriceTrendIfo> priceList;

	public String getGoodClassName() {
		return goodClassName;
	}

	public void setGoodClassName(String goodClassName) {
		this.goodClassName = goodClassName;
	}

	public String getGoodClassId() {
		return goodClassId;
	}

	public void setGoodClassId(String goodClassId) {
		this.goodClassId = goodClassId;
	}

	public List<PriceTrendIfo> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<PriceTrendIfo> priceList) {
		this.priceList = priceList;
	}
}
