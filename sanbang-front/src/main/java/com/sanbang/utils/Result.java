package com.sanbang.utils;

import java.io.Serializable;

public class Result implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5559674683734298678L;
	
	private Boolean success;
	private String msg;// 信息
	private Object obj;
	private String errorcode;
	private Page meta;//存放分页数据
	
	public Result() {
	}
	
	public Result(Boolean success, String msg, Object obj, String errorcode, Page meta) {
		super();
		this.success = success;
		this.msg = msg;
		this.obj = obj;
		this.errorcode = errorcode;
		this.meta = meta;
	}



	public static Result success() {
		Result result = new Result();
		result.setErrorcode("000");
		result.setSuccess(true);
		result.setMsg("请求成功");
		return result;
	}

	public static Result failure() {
		Result result = new Result();
		result.setErrorcode("888");
		result.setSuccess(false);
		result.setMsg("请求失败");
		return result;
	}
	
	
	
	public Result(Boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public Page getMeta() {
		return meta;
	}

	public void setMeta(Page meta) {
		this.meta = meta;
	}

}
