package com.sanbang.vo;

/**
 * 
 * @author zhangxiantao 2016-9-2
 * @version v1.0
 */
public class Result {

	/** 状态码*/
	private String code;
	
	/** 信息*/
	private String info;
	
	/** 返回内容*/
	private Object model;
	
	public static Result success() {
		Result result = new Result();
		result.setCode("200");
		result.setInfo("success");
		return result;
	}

	public static Result failure() {
		Result result = new Result();
		result.setCode("500");
		result.setInfo("failure");
		return result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
}
