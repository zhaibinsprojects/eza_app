package com.sanbang.seller.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.utils.Result;

public interface SellerActivateService {

	Result addActivateInfo(Result result, String companyName, String yTurnover, String covered, String rent, String device_num,
			String employee_num, String assets, String open_bank_name, String openBankNo, String open_branch_name,
			String open_branch_no, String location_detail, String obtainYear, HttpServletRequest request, HttpServletResponse response);

	

}
