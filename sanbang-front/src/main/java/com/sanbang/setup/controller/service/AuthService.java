package com.sanbang.setup.controller.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface AuthService {

	/**
	 * 保存公司信息
	 */
	public Result saveComAuth(Result result, HttpServletRequest request,ezs_user upi,HttpServletResponse response);
	
	
}