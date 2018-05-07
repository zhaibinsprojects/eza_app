package com.sanbang.bin;

import java.io.Serializable;


/**
 * 系统信息实体类
 * 
 * @author zhangxiantao
 *  
 * 2016年10月12日
 */
public class SystemMessageInfo implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId;
	private Long orderNum;				// 订单数量
	private Long totalAmount;			// 交易额
	private Long companyAmount;			// 最大的公司交易额 
	private String company;				// 交易额最大的公司
	private String payOrderAmount;		// 单笔最大交易额
	private String buyer;				// 最大单笔交易的买家
	private String saler;				// 最大单笔交易的卖家
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Long getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}
	public Long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getCompanyAmount() {
		return companyAmount;
	}
	public void setCompanyAmount(Long companyAmount) {
		this.companyAmount = companyAmount;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPayOrderAmount() {
		return payOrderAmount;
	}
	public void setPayOrderAmount(String payOrderAmount) {
		this.payOrderAmount = payOrderAmount;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getSaler() {
		return saler;
	}
	public void setSaler(String saler) {
		this.saler = saler;
	}
	@Override
	public String toString() {
		return "SystemMessageInfo [userId=" + userId + ", orderNum=" + orderNum
				+ ", totalAmount=" + totalAmount + ", companyAmount="
				+ companyAmount + ", company=" + company + ", payOrderAmount="
				+ payOrderAmount + ", buyer=" + buyer + ", saler=" + saler
				+ "]";
	}
	
}
