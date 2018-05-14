package com.sanbang.seller.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.utils.Result;

public interface SellerContractService {

	Object contractManage(Result result, HttpServletRequest request, HttpServletResponse response);

	Object queryContractInfo(Long pactId, Result result, HttpServletRequest request, HttpServletResponse response);

	Object queryContractByIdAndDate(Result result, String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response);

}
