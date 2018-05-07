package com.sanbang.utils;

import java.io.Serializable;

/**
 * 保证金管理模糊查询
 * 
 * @author langjf
 *
 */
public class DepositPager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 签订方账号
	private String userName;
	// 签订公司
	private String memberName;
	// 提交人
	private String submitName;
	// 开始时间
	private String startTime;
	// 结束时间
	private String endTime;
	// 买方
	private String buyName;
	// 卖方
	private String sellName;
	// 交易地点
	private String sellPlace;
	private String phone;
	// 状态
	private int status=20;

	private int pageSize = 8;
	private int pageCount = 1;
	private int pageNo = 1;
	private int totolPage = 1;
	//当前条数
	private int page;

	public String getBuyName() {
		return buyName;
	}

	public void setBuyName(String buyName) {
		this.buyName = buyName;
	}

	public String getSellName() {
		return sellName;
	}

	public void setSellName(String sellName) {
		this.sellName = sellName;
	}

	public String getSellPlace() {
		return sellPlace;
	}

	public void setSellPlace(String sellPlace) {
		this.sellPlace = sellPlace;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTotolPage() {
		return totolPage;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setTotolPage(int totolPage) {
		this.totolPage = totolPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public DepositPager() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSubmitName() {
		return submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
