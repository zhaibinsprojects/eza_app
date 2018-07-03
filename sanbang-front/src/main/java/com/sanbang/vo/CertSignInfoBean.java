package com.sanbang.vo;

import java.io.Serializable;
import java.util.Date;

//
public class CertSignInfoBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8081408201525419095L;

	/**
	 * 
	 */

	private String signcomFrout;//储蓄卡用户名
	
	private String signUrl;//储蓄卡号
	
	private String contentno;//身份证号
	
	private String orderid;//银行预留手机号
    
	private String signTime;//返回状态信息
	
	private int status;

	public String getSigncomFrout() {
		return signcomFrout;
	}

	public void setSigncomFrout(String signcomFrout) {
		this.signcomFrout = signcomFrout;
	}

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

	public String getContentno() {
		return contentno;
	}

	public void setContentno(String contentno) {
		this.contentno = contentno;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getSignTime() {
		return signTime;
	}

	public void setSignTime(String signTime) {
		this.signTime = signTime;
	}
	
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CertSignInfoBean [signcomFrout=" + signcomFrout + ", signUrl=" + signUrl + ", contentno=" + contentno
				+ ", orderid=" + orderid + ", signTime=" + signTime + ", status=" + status + "]";
	}
	
}
