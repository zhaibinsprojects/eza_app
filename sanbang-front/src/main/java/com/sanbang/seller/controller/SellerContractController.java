package com.sanbang.seller.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanbang.seller.service.SellerContractService;
import com.sanbang.utils.Result;

@RestController
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
	
	@RequestMapping("/queryContractByIdAndDate")
	public Object queryContractByIdAndDate(String orderno, String startTime,String endTime,HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		Object object = sellerContractService.queryContractByIdAndDate(result, orderno, startTime, endTime, request, response);
		return object;
	} 
	
	
	
	
	
	
	
	
	
	
	
	
}
