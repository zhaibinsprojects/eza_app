package com.sanbang.vo;

import java.math.BigDecimal;

public class GoodsListInfo {
	
	private Long id;
	
	private Double cncl_num;
	
	private String name;
	
	private BigDecimal price;
	
	private Integer status;
	
	private Integer audit_status;

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
	
}
