package com.sanbang.seller.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_pact;
import com.sanbang.utils.Result;

public interface SellerContractService {

	Object contractManage(Result result, HttpServletRequest request, HttpServletResponse response);

	Object queryContractInfo(Long pactId, Result result, HttpServletRequest request, HttpServletResponse response);

	Object queryContractByIdOrDate(String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response);

}
