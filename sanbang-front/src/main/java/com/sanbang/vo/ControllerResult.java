package com.sanbang.vo;

/**
 * 用于Controller中处理Json返回值
 * 
 * @author zhangxiantao
 *  
 * 2016年6月24日
 */
public class ControllerResult {

	private String responseCode;
	
	private String message;
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ControllerResult() {
	}
	
}
