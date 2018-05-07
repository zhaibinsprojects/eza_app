package com.sanbang.redis;

import java.io.Serializable;

/**
 * @author zhangxiantao
 * 2016年4月26日 描述：
 */
public class RedisResult<T> implements Serializable {

	/**
	 * @author zhangxiantao
	 * 2016年4月26日 描述：
	 */
	private static final long serialVersionUID = -6494123053925520988L;
	//返回功能码
	private int code;
	//返回信息
	private String errMsg;
	//返回结果集
	private T result;

	/**
	 * @return getCode获取 code 的值
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param setCode 设置  code 的值
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return getErrMsg获取 errMsg 的值
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param setErrMsg 设置  errMsg 的值
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return getResult获取 result 的值
	 */
	public T getResult() {
		return result;
	}

	/**
	 * @param setResult 设置  result 的值
	 */
	public void setResult(T result) {
		this.result = result;
	}
	
}
