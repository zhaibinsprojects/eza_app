package com.sanbang.vo;

import java.util.Date;

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
    //图片给个绝对路径即可
    private String picturePath;
    //提货周期
    private Integer pickup_cycle;
    //提货时间
    private Date pickup_date;

	//是否为子公司商品（是否走简易流程） 0-否 1-是
	private int isChildrenGood;

	public int getIsChildrenGood() {
		return isChildrenGood;
	}
	public void setIsChildrenGood(int isChildrenGood) {
		this.isChildrenGood = isChildrenGood;
	}
    public Integer getPickup_cycle() {
		return pickup_cycle;
	}
	public void setPickup_cycle(Integer pickup_cycle) {
		this.pickup_cycle = pickup_cycle;
	}
	public Date getPickup_date() {
		return pickup_date;
	}
	public void setPickup_date(Date pickup_date) {
		this.pickup_date = pickup_date;
	}
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
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
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
