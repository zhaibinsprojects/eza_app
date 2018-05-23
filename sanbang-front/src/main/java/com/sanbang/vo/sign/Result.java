package com.sanbang.vo.sign;

/**
 * 
 * @author zhangxiantao 2016-9-2
 * @version v1.0
 */
public class Result {

	/** 状态码*/
	private boolean success;
	
	/** 信息*/
	private String message;
	
	/** 返回内容*/
	private Object content = "";
	
	public static Result success() {
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("请求成功");
		return result;
	}

	public static Result failure() {
		Result result = new Result();
		result.setSuccess(false);
		result.setMessage("请求失败");
		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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

	public Result() {
	}

	@Override
	public String toString() {
		return "Result [success=" + success + ", message=" + message
				+ ", content=" + content.toString() + "]";
	}
	
}
