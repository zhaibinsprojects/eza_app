package com.sanbang.vo;

import com.sanbang.bean.ezs_price_trend;

public class PriceTrendIfo extends ezs_price_trend {
	
	private String goodClassName;
	private String goodColorName;
	private String goodFormName;
	private Double goodPrice;
	private String goodArea;
	private String sandByOne;
	private String sandBytwo;
	private String sandBytree;
	
	public String getGoodClassName() {
		return goodClassName;
	}
	public void setGoodClassName(String goodClassName) {
		this.goodClassName = goodClassName;
	}
	public String getGoodColorName() {
		return goodColorName;
	}
	public void setGoodColorName(String goodColorName) {
		this.goodColorName = goodColorName;
	}
	public String getGoodFormName() {
		return goodFormName;
	}
	public void setGoodFormName(String goodFormName) {
		this.goodFormName = goodFormName;
	}
	public Double getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(Double goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getGoodArea() {
		return goodArea;
	}
	public void setGoodArea(String goodArea) {
		this.goodArea = goodArea;
	}
	public String getSandByOne() {
		return sandByOne;
	}
	public void setSandByOne(String sandByOne) {
		this.sandByOne = sandByOne;
	}
	public String getSandBytwo() {
		return sandBytwo;
	}
	public void setSandBytwo(String sandBytwo) {
		this.sandBytwo = sandBytwo;
	}
	public String getSandBytree() {
		return sandBytree;
	}
	public void setSandBytree(String sandBytree) {
		this.sandBytree = sandBytree;
	}
	public PriceTrendIfo(String goodClassName, String goodColorName, String goodFormName, Double goodPrice,
			String goodArea) {
		super();
		this.goodClassName = goodClassName;
		this.goodColorName = goodColorName;
		this.goodFormName = goodFormName;
		this.goodPrice = goodPrice;
		this.goodArea = goodArea;
	}
	public PriceTrendIfo() {
		super();
	}
}
