package com.sanbang.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ezs_goods  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8100261341395037842L;

	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String addess;

    private String addess2;

    private String ash;

    private String bending;

    private String burning;

    private String cantilever;

    private Integer click;

    private Double cncl_num;

    private Integer collect;

    private String content;

    private String crack;

    private String density;

    private String flexural;

    private String freely;

    private String good_no;

    private Boolean good_self;

    private Integer goods_salenum;

    private Double inventory;

    private String keyword;

    private Date lastModifyDate;

    private String lipolysis;

    private Boolean lockStatus;

    private Boolean memberLook;

    private String name;

    private BigDecimal price;

    private int protection;

    private String purpose;
 
    private Boolean recommend;

    private Date recommend_time;

    private String seo_description;

    private String source;

    private Integer status;

    private String tensile;

    private Integer validity;

    private String water;

    private Long area_id;

    private Long classOfFinish_id;

    private Long color_id;

    private Long form_id;

    private Long goodClass_id;

    private Long goods_main_photo_id;

    private Long logistics_id;

    private Long quality_id;

    private Long region_id;

    private Long supply_id;

    private Long user_id;

    private Long util_id;

    private Long supplier_id;

    private String ash2;

    private String bending2;

    private String burning2;

    private String cantilever2;

    private String crack2;

    private String density2;

    private String flexural2;

    private String freely2;

    private String lipolysis2;

    private BigDecimal saleprice;

    private String tensile2;

    private String water2;

    private Integer review_status;

    private String good_num;

    private Integer manage_status;

    private Integer protection_v;
    //取货周期
    private Integer pickup_cycle;
    
    public Integer getPickup_cycle() {
		return pickup_cycle;
	}

	public void setPickup_cycle(Integer pickup_cycle) {
		this.pickup_cycle = pickup_cycle;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

    public String getAddess() {
        return addess;
    }

    public void setAddess(String addess) {
        this.addess = addess == null ? null : addess.trim();
    }

    public String getAddess2() {
        return addess2;
    }

    public void setAddess2(String addess2) {
        this.addess2 = addess2 == null ? null : addess2.trim();
    }

    public String getAsh() {
        return ash;
    }

    public void setAsh(String ash) {
        this.ash = ash == null ? null : ash.trim();
    }

    public String getBending() {
        return bending;
    }

    public void setBending(String bending) {
        this.bending = bending == null ? null : bending.trim();
    }

    public String getBurning() {
        return burning;
    }

    public void setBurning(String burning) {
        this.burning = burning == null ? null : burning.trim();
    }

    public String getCantilever() {
        return cantilever;
    }

    public void setCantilever(String cantilever) {
        this.cantilever = cantilever == null ? null : cantilever.trim();
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public Double getCncl_num() {
        return cncl_num;
    }

    public void setCncl_num(Double cncl_num) {
        this.cncl_num = cncl_num;
    }

    public Integer getCollect() {
        return collect;
    }

    public void setCollect(Integer collect) {
        this.collect = collect;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCrack() {
        return crack;
    }

    public void setCrack(String crack) {
        this.crack = crack == null ? null : crack.trim();
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density == null ? null : density.trim();
    }

    public String getFlexural() {
        return flexural;
    }

    public void setFlexural(String flexural) {
        this.flexural = flexural == null ? null : flexural.trim();
    }

    public String getFreely() {
        return freely;
    }

    public void setFreely(String freely) {
        this.freely = freely == null ? null : freely.trim();
    }

    public String getGood_no() {
        return good_no;
    }

    public void setGood_no(String good_no) {
        this.good_no = good_no == null ? null : good_no.trim();
    }

    public Boolean getGood_self() {
        return good_self;
    }

    public void setGood_self(Boolean good_self) {
        this.good_self = good_self;
    }

    public Integer getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(Integer goods_salenum) {
        this.goods_salenum = goods_salenum;
    }

    public Double getInventory() {
        return inventory;
    }

    public void setInventory(Double inventory) {
        this.inventory = inventory;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getLipolysis() {
        return lipolysis;
    }

    public void setLipolysis(String lipolysis) {
        this.lipolysis = lipolysis == null ? null : lipolysis.trim();
    }

    public Boolean getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Boolean getMemberLook() {
        return memberLook;
    }

    public void setMemberLook(Boolean memberLook) {
        this.memberLook = memberLook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    

    public int getProtection() {
		return protection;
	}

	public void setProtection(int protection) {
		this.protection = protection;
	}

	public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Date getRecommend_time() {
        return recommend_time;
    }

    public void setRecommend_time(Date recommend_time) {
        this.recommend_time = recommend_time;
    }

    public String getSeo_description() {
        return seo_description;
    }

    public void setSeo_description(String seo_description) {
        this.seo_description = seo_description == null ? null : seo_description.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTensile() {
        return tensile;
    }

    public void setTensile(String tensile) {
        this.tensile = tensile == null ? null : tensile.trim();
    }

    public Integer getValidity() {
        return validity;
    }

    public void setValidity(Integer validity) {
        this.validity = validity;
    }

    public String getWater() {
        return water;
    }

    public void setWater(String water) {
        this.water = water == null ? null : water.trim();
    }

    public Long getArea_id() {
        return area_id;
    }

    public void setArea_id(Long area_id) {
        this.area_id = area_id;
    }

    public Long getClassOfFinish_id() {
        return classOfFinish_id;
    }

    public void setClassOfFinish_id(Long classOfFinish_id) {
        this.classOfFinish_id = classOfFinish_id;
    }

    public Long getColor_id() {
        return color_id;
    }

    public void setColor_id(Long color_id) {
        this.color_id = color_id;
    }

    public Long getForm_id() {
        return form_id;
    }

    public void setForm_id(Long form_id) {
        this.form_id = form_id;
    }

    public Long getGoodClass_id() {
        return goodClass_id;
    }

    public void setGoodClass_id(Long goodClass_id) {
        this.goodClass_id = goodClass_id;
    }

    public Long getGoods_main_photo_id() {
        return goods_main_photo_id;
    }

    public void setGoods_main_photo_id(Long goods_main_photo_id) {
        this.goods_main_photo_id = goods_main_photo_id;
    }

    public Long getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(Long logistics_id) {
        this.logistics_id = logistics_id;
    }

    public Long getQuality_id() {
        return quality_id;
    }

    public void setQuality_id(Long quality_id) {
        this.quality_id = quality_id;
    }

    public Long getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }

    public Long getSupply_id() {
        return supply_id;
    }

    public void setSupply_id(Long supply_id) {
        this.supply_id = supply_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getUtil_id() {
        return util_id;
    }

    public void setUtil_id(Long util_id) {
        this.util_id = util_id;
    }

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getAsh2() {
        return ash2;
    }

    public void setAsh2(String ash2) {
        this.ash2 = ash2 == null ? null : ash2.trim();
    }

    public String getBending2() {
        return bending2;
    }

    public void setBending2(String bending2) {
        this.bending2 = bending2 == null ? null : bending2.trim();
    }

    public String getBurning2() {
        return burning2;
    }

    public void setBurning2(String burning2) {
        this.burning2 = burning2 == null ? null : burning2.trim();
    }

    public String getCantilever2() {
        return cantilever2;
    }

    public void setCantilever2(String cantilever2) {
        this.cantilever2 = cantilever2 == null ? null : cantilever2.trim();
    }

    public String getCrack2() {
        return crack2;
    }

    public void setCrack2(String crack2) {
        this.crack2 = crack2 == null ? null : crack2.trim();
    }

    public String getDensity2() {
        return density2;
    }

    public void setDensity2(String density2) {
        this.density2 = density2 == null ? null : density2.trim();
    }

    public String getFlexural2() {
        return flexural2;
    }

    public void setFlexural2(String flexural2) {
        this.flexural2 = flexural2 == null ? null : flexural2.trim();
    }

    public String getFreely2() {
        return freely2;
    }

    public void setFreely2(String freely2) {
        this.freely2 = freely2 == null ? null : freely2.trim();
    }

    public String getLipolysis2() {
        return lipolysis2;
    }

    public void setLipolysis2(String lipolysis2) {
        this.lipolysis2 = lipolysis2 == null ? null : lipolysis2.trim();
    }

   

    public BigDecimal getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}

	public String getTensile2() {
        return tensile2;
    }

    public void setTensile2(String tensile2) {
        this.tensile2 = tensile2 == null ? null : tensile2.trim();
    }

    public String getWater2() {
        return water2;
    }

    public void setWater2(String water2) {
        this.water2 = water2 == null ? null : water2.trim();
    }

    public Integer getReview_status() {
        return review_status;
    }

    public void setReview_status(Integer review_status) {
        this.review_status = review_status;
    }

    public String getGood_num() {
        return good_num;
    }

    public void setGood_num(String good_num) {
        this.good_num = good_num == null ? null : good_num.trim();
    }

    public Integer getManage_status() {
        return manage_status;
    }

    public void setManage_status(Integer manage_status) {
        this.manage_status = manage_status;
    }

    public Integer getProtection_v() {
        return protection_v;
    }

    public void setProtection_v(Integer protection_v) {
        this.protection_v = protection_v;
    }
}