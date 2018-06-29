package com.sanbang.vo;

import javax.servlet.jsp.PageContext;

public class QueryCondition {

	private Long goodId;

	private Long userId;

	private Long storeId;

	private Long storeCarId;

	private Integer storeCarStatus;

	private int pagesize=10;

	private int pageno = 1;

	private long pageCount;

	public Integer getStoreCarStatus() {
		return storeCarStatus;
	}

	public void setStoreCarStatus(Integer storeCarStatus) {
		this.storeCarStatus = storeCarStatus;
	}

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

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
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
