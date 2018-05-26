package com.sanbang.seller.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.utils.Result;

public interface SellerContractService {


	Object queryContractInfo(Long pactId, Result result, HttpServletRequest request, HttpServletResponse response);

	Map<String, Object> contractManage(String currentPage, Long sellerId);

	Map<String, Object> queryContractByIdOrDate(String orderno, String startTime, String endTime, String currentPage,
			Long sellerId);

}
