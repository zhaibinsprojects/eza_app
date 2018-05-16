package com.sanbang.seller.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_pact;
import com.sanbang.seller.service.SellerContractService;
import com.sanbang.utils.Result;

@Controller
@RequestMapping("/seller")
public class SellerContractController {
	
	@Autowired
	SellerContractService sellerContractService;
	
	/**
	 * 合同管理，展示合同列表
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/contractManage")
	public Object contractManage(HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Object object = sellerContractService.contractManage(result, request, response);
		return object;
	}
	/**
	 * 查看合同详情
	 * @param pactId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryContractInfo")
	public Object queryContractInfo(Long pactId, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Object object = sellerContractService.queryContractInfo(pactId, result, request, response);
		return object;
	}
	/**
	 * 根据订单编号和时间搜索合同
	 * @param orderno
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryContractByIdOrDate")
	public Object queryContractByIdOrDate(String orderno, String startTime,String endTime,HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Object object = sellerContractService.queryContractByIdOrDate(orderno, startTime, endTime, request, response);
		return object;
	} 
}
