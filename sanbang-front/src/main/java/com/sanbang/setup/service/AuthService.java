package com.sanbang.setup.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface AuthService {

	/**
	 * 保存公司信息
	 */
	public Result saveComAuth(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response);

	/**
	 * 保存公司税号
	 */
	public Result saveComEin(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response);

	/**
	 * 个体保存公司信息
	 */
	public Result saveindivAuth(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response);

	/**
	 * 个体暂存税号
	 */
	public Result saveindivEin(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response);

	/**
	 * 提交信息
	 * @param result
	 * @param request
	 * @param upi
	 * @param response
	 * @param flag
	 * @return
	 */
	public Result saveAuthInfo(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response,
			String flag);
	
	/**
	 * 个体暂存税号
	 */
	public Result savePicUrl(Result result, HttpServletRequest request, ezs_user upi, HttpServletResponse response);
	
}
