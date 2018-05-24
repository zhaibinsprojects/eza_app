package com.sanbang.seller.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.seller.service.SellerActivateService;
import com.sanbang.utils.Result;

@Controller
@RequestMapping("/seller")
public class SellerActivateController {
	
	@Autowired
	SellerActivateService activateService;
	/**
	 * 供应商激活
	 * @param companyName
	 * @param yTurnover
	 * @param covered
	 * @param rent
	 * @param device_num
	 * @param employee_num
	 * @param assets
	 * @param open_bank_name
	 * @param openBankNo
	 * @param open_branch_name
	 * @param open_branch_no
	 * @param location_detail
	 * @return
	 */
	@RequestMapping("/sellerActivate") // 固定产值，经营年限  找不到对应字段
	@ResponseBody
	public Object sellerActivate(String companyName, String yTurnover, String covered, String rent, String device_num,
			String employee_num, String assets, String obtainYear,String open_bank_name, String openBankNo, String open_branch_name,
			String open_branch_no, String location_detail, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		result = activateService.addActivateInfo(result, companyName, yTurnover, covered, rent, device_num, employee_num, assets, 
				obtainYear, open_bank_name, openBankNo, open_branch_name, open_branch_no, location_detail, request, response);
		return result;
	}
}
