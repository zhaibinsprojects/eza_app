package com.sanbang.vo;

import java.math.BigDecimal;

public class GoodsListInfo {
	
	private Long id;
	
	private Double cncl_num;
	
	private Double inventory;
	
	private String name;
	
	private BigDecimal price;
	
	private Integer status;
	// 商品初审状态码 540.待审核 ，544.初审不通过，541.初审通过/待质检（复审），546.复审通过 ，547.复审不通过
	private Integer audit_status;
	// 商品定价状态码，600.待定价，601.定价审核中，602.定价审核不通过，603.定价审核通过
	private Integer priceStatus;
	
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
	
}
