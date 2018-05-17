package com.sanbang.vo;

import java.util.Date;

import com.sanbang.bean.ezs_price_trend;

public class PriceTrendIfo extends ezs_price_trend {
	
	private String goodClassName;
	private String goodColorName;
	private String goodFormName;
	private Double goodPrice;
	private String goodArea;
	//当日均价
	private Double currentAVGPrice;
	//前一日均价
	private Double preAVGPrice;
	//成交日期
	private String dealDate;
	//涨幅
	private String sandByOne;
	//品类名称
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
	public Double getCurrentAVGPrice() {
		return currentAVGPrice;
	}
	public void setCurrentAVGPrice(Double currentAVGPrice) {
		this.currentAVGPrice = currentAVGPrice;
	}
	public Double getPreAVGPrice() {
		return preAVGPrice;
	}
	public void setPreAVGPrice(Double preAVGPrice) {
		this.preAVGPrice = preAVGPrice;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
}
