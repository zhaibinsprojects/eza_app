package com.sanbang.vo;

/**
 * 
 * @author zhangxiantao 2017-01-12
 * @version v2.0
 */
public class AppResult {

	/** 状态码*/
	private String status;
	
	/** 信息*/
	private String message;
	
	/** 返回内容*/
	private Object content = "";
	
	public static AppResult success() {
		AppResult result = new AppResult();
		result.setStatus("1");
		result.setMessage("请求成功");
		return result;
	}

	public static AppResult failure() {
		AppResult result = new AppResult();
		result.setStatus("0");
		result.setMessage("请求失败");
		return result;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	public AppResult() {
	}

}
