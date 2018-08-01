package com.sanbang.utils;

import java.io.Serializable;

import com.sanbang.vo.DictionaryCode;

public class Result implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2578337283900400848L;

	
	private Boolean success;
	private String msg;// 信息
	private Object obj;
	private int errorcode;
	private Page meta;//存放分页数据
	private int count;
	
	public Result() {
	}

	
	public static Result success() {
		Result result = new Result();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setSuccess(true);
		result.setMsg("请求成功");
		return result;
	}

	public static Result failure() {
		Result result = new Result();
		result.setSuccess(false);
		return result;
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

	public int getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(int errorcode) {
		this.errorcode = errorcode;
	}

	public Page getMeta() {
		return meta;
	}

	public void setMeta(Page meta) {
		this.meta = meta;
	}

	
	
	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public Result(Boolean success, String msg, Object obj, int errorcode, Page meta) {
		super();
		this.success = success;
		this.msg = msg;
		this.obj = obj;
		this.errorcode = errorcode;
		this.meta = meta;
	}
	
}
