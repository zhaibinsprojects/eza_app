package com.sanbang.vo;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;

public class GoodsInfo extends ezs_goods{
	
	private ezs_accessory mainPhoto;
	
	private String colorName;
    private String formName;
    private String areaName;
    private String goodClassName;
	
	public String getColorName() {
		return colorName;
	}
	public void setColorName(String colorName) {
		this.colorName = colorName;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getGoodClassName() {
		return goodClassName;
	}
	public void setGoodClassName(String goodClassName) {
		this.goodClassName = goodClassName;
	}
	public ezs_accessory getMainPhoto() {
		return mainPhoto;
	}
	public void setMainPhoto(ezs_accessory mainPhoto) {
		this.mainPhoto = mainPhoto;
	}
	public GoodsInfo(ezs_accessory mainPhoto) {
		super();
		this.mainPhoto = mainPhoto;
	}
	public GoodsInfo() {
		super();
	}
}
