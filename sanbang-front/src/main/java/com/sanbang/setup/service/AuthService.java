package com.sanbang.setup.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface AuthService {

	/**
	 * 暂存保存公司信息
	 */
	public Result saveComAuth(Result result, HttpServletRequest request,ezs_user upi,HttpServletResponse response);
	
	/**
	 * 暂存税号
	 */
	public Result saveComein(Result result, HttpServletRequest request,ezs_user upi,HttpServletResponse response);
}
