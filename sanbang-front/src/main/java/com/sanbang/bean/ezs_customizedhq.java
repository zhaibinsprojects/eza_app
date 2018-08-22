package com.sanbang.bean;

import java.util.Date;
import java.util.List;

import com.sanbang.vo.goods.ezs_Dzgoods_classVo;
import com.sanbang.vo.hangq.HangqDzAreaVo;

public class ezs_customizedhq {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String areaids;//地区组合

    private String category;//分类组合

    private Boolean isPush;//是否推送

    private String pushMethod;//推送方式

    private String title;//栏目

    private Long store_id;

    private Long user_id;

    private Long area_id;

    private Long priceTrend_id;
    
    private List<HangqDzAreaVo> areaNames;
    
    private List<ezs_Dzgoods_classVo > categorys;

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

    

    public String getAreaids() {
		return areaids;
	}

	public void setAreaids(String areaids) {
		this.areaids = areaids;
	}

	public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public Boolean getIsPush() {
        return isPush;
    }

    public void setIsPush(Boolean isPush) {
        this.isPush = isPush;
    }

    public String getPushMethod() {
        return pushMethod;
    }

    public void setPushMethod(String pushMethod) {
        this.pushMethod = pushMethod == null ? null : pushMethod.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getArea_id() {
        return area_id;
    }

    public void setArea_id(Long area_id) {
        this.area_id = area_id;
    }

    public Long getPriceTrend_id() {
        return priceTrend_id;
    }

    public void setPriceTrend_id(Long priceTrend_id) {
        this.priceTrend_id = priceTrend_id;
    }

	

	public List<HangqDzAreaVo> getAreaNames() {
		return areaNames;
	}

	public void setAreaNames(List<HangqDzAreaVo> areaNames) {
		this.areaNames = areaNames;
	}

	public List<ezs_Dzgoods_classVo> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<ezs_Dzgoods_classVo> categorys) {
		this.categorys = categorys;
	}

	
}