package com.sanbang.vo;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;

public class GoodsInfo extends ezs_goods{
	
	private ezs_accessory mainPhoto;
	
	private String colorName;
    private String formName;
    private String areaName;
    private String goodClassName;
    private String utilName;
    
    //照片相关的
    private String pictureSuffix;	//图片后缀名
    private String pictureName;	//图片名称
    private String picturePath;	//图片路径
    
	
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
	public String getPictureSuffix() {
		return pictureSuffix;
	}
	public void setPictureSuffix(String pictureSuffix) {
		this.pictureSuffix = pictureSuffix;
	}
	public String getPictureName() {
		return pictureName;
	}
	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public String getUtilName() {
		return utilName;
	}
	public void setUtilName(String utilName) {
		this.utilName = utilName;
	}
	
}
