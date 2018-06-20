package com.sanbang.vo;

public class GoodsOfOrderInfo {
	
	private Long goodsCartID;
	private Long goodsID;
	private String goodsName;
	private boolean status;
	private String message;
	public Long getGoodsCartID() {
		return goodsCartID;
	}
	public void setGoodsCartID(Long goodsCartID) {
		this.goodsCartID = goodsCartID;
	}
	public Long getGoodsID() {
		return goodsID;
	}
	public void setGoodsID(Long goodsID) {
		this.goodsID = goodsID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public GoodsOfOrderInfo() {
		super();
	}
	
}
