package com.sanbang.bean;

import java.util.Date;

public class ezs_address {
	
	public static final Boolean ADDRESS_DELETESTATUS_0 = false;//启用
	public static final Boolean ADDRESS_DELETESTATUS_1 = true;//不启用
	public static final Boolean ADDRESS_BESTOW_0 = false;//默认
	public static final Boolean ADDRESS_BESTOW_1 = true;//非默认
	
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String area_info;

    private Boolean bestow=true;

    private String mobile;

    private String telephone;

    private String trueName;

    private String zip;

    private Long area_id;

    private Long user_id;
    
    private String area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getArea_info() {
        return area_info;
    }

    public void setArea_info(String area_info) {
        this.area_info = area_info == null ? null : area_info.trim();
    }

    public Boolean getBestow() {
        return bestow;
    }

    public void setBestow(Boolean bestow) {
        this.bestow = bestow;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip == null ? null : zip.trim();
    }

    public Long getArea_id() {
        return area_id;
    }

    public void setArea_id(Long area_id) {
        this.area_id = area_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
    
}