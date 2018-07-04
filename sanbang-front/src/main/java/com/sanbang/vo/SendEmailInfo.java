package com.sanbang.vo;

import java.util.Date;

public class SendEmailInfo {
	
	private Long userId;
	
	private Date sendTime;
	
	private Date nextSendTime;
	
	private Double sendCount;
	
	private String standByOne;
	
	private String standByTwo;
	
	private String standByTree;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getNextSendTime() {
		return nextSendTime;
	}
	public void setNextSendTime(Date nextSendTime) {
		this.nextSendTime = nextSendTime;
	}
	public Double getSendCount() {
		return sendCount;
	}
	public void setSendCount(Double sendCount) {
		this.sendCount = sendCount;
	}
	public String getStandByOne() {
		return standByOne;
	}
	public void setStandByOne(String standByOne) {
		this.standByOne = standByOne;
	}
	public String getStandByTwo() {
		return standByTwo;
	}
	public void setStandByTwo(String standByTwo) {
		this.standByTwo = standByTwo;
	}
	public String getStandByTree() {
		return standByTree;
	}
	public void setStandByTree(String standByTree) {
		this.standByTree = standByTree;
	}
}
