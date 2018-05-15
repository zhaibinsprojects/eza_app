package com.sanbang.setup.controller.service;

import javax.servlet.http.HttpServletRequest;

import com.sanbang.utils.Result;

public interface AuthService {

	/**
	 * 保存公司信息
	 */
	public Result saveComAuth(Result result,HttpServletRequest request);
	
	
}
