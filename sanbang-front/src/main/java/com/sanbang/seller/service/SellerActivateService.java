package com.sanbang.seller.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface SellerActivateService {

	Result addActivateInfo(Result result, ezs_user upi, String companyName, String yTurnover, String covered, String rent, String device_num,
			String employee_num, String assets, String obtainYear, HttpServletRequest request, HttpServletResponse response);

}
