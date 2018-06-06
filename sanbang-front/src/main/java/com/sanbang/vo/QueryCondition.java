package com.sanbang.vo;

public class QueryCondition {
	
	private Long goodId;
	
	private Long userId;
	
	private Long storeId;
	
	private Long storeCarId;
	
	public Long getStoreCarId() {
		return storeCarId;
	}
	public void setStoreCarId(Long storeCarId) {
		this.storeCarId = storeCarId;
	}
	public Long getStoreId() {
		return storeId;
	}
	public void setStoreId(Long storeId) {
		this.storeId = storeId;
	}
	public Long getGoodId() {
		return goodId;
	}
	public void setGoodId(Long goodId) {
		this.goodId = goodId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public QueryCondition(Long goodId, Long userId) {
		super();
		this.goodId = goodId;
		this.userId = userId;
	}
	public QueryCondition() {
		super();
	}
}
