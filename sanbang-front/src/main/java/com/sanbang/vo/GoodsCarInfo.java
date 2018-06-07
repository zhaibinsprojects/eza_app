package com.sanbang.vo;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goodscart;

public class GoodsCarInfo extends ezs_goodscart {
	//图片
	private ezs_accessory mainPhoto;
	private String colorName;
    private String formName;
    //库存地址
    private Long areaId;
	private String areaName;
    private String goodClassName;
    //库存
    private Double inventory;
    //商品单位
    private String utilName;
    
    private String goodName;
    
    
    public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getUtilName() {
		return utilName;
	}
	public void setUtilName(String utilName) {
		this.utilName = utilName;
	}
	public ezs_accessory getMainPhoto() {
		return mainPhoto;
	}
	public void setMainPhoto(ezs_accessory mainPhoto) {
		this.mainPhoto = mainPhoto;
	}
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
	public Double getInventory() {
		return inventory;
	}
	public void setInventory(Double inventory) {
		this.inventory = inventory;
	}
	public GoodsCarInfo(ezs_accessory mainPhoto, String colorName, String formName, String areaName,
			String goodClassName, Double inventory) {
		super();
		this.mainPhoto = mainPhoto;
		this.colorName = colorName;
		this.formName = formName;
		this.areaName = areaName;
		this.goodClassName = goodClassName;
		this.inventory = inventory;
	}
	public GoodsCarInfo() {
		super();
	}
	@Override
	public String toString() {
		return "GoodsCarInfo [mainPhoto=" + mainPhoto + ", colorName=" + colorName + ", formName=" + formName
				+ ", areaName=" + areaName + ", goodClassName=" + goodClassName + ", inventory=" + inventory + "]";
	}
}
