package com.sanbang.vo;

import java.math.BigDecimal;
import java.util.Date;

public class GoodsListInfo {
	
	private Long id;
	
	private Double cncl_num;
	
	private Double inventory;
	
	private String name;
	
	private BigDecimal price;
	
	private BigDecimal saleprice;
	
	private Integer status;
	// 商品初审状态码 540.待审核 ，544.初审不通过，541.初审通过/待质检（复审），546.复审通过 ，547.复审不通过
	private Integer audit_status;
	// 商品定价状态码，600.待定价，601.定价审核中，602.定价审核不通过，603.定价审核通过
	private Integer priceStatus;
	
	private String  lastModifyDate;
	 //提货周期
    private Integer pickup_cycle;
    //提货时间
    private Date pickup_date; 
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getCncl_num() {
		return cncl_num;
	}

	public void setCncl_num(Double cncl_num) {
		this.cncl_num = cncl_num;
	}

	public Double getInventory() {
		return inventory;
	}

	public void setInventory(Double inventory) {
		this.inventory = inventory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(BigDecimal saleprice) {
		this.saleprice = saleprice;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(Integer audit_status) {
		this.audit_status = audit_status;
	}

	public Integer getPriceStatus() {
		return priceStatus;
	}

	public void setPriceStatus(Integer priceStatus) {
		this.priceStatus = priceStatus;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
	
}
