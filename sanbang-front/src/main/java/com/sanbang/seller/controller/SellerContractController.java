package com.sanbang.seller.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_user;
import com.sanbang.seller.service.SellerContractService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

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
	public Object contractManage(HttpServletRequest request, HttpServletResponse response, String currentPage){
		Map<String, Object> map = null;
		Result result = Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long sellerId = upi.getId();
		
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		map = sellerContractService.contractManage(currentPage, sellerId);
		List<ezs_pact> list = new ArrayList<>();
		
		list = (List<ezs_pact>)map.get("Obj");
		
		int errorCode = (int) map.get("ErrorCode");
		
		page = (Page) map.get("page");
		result.setObj(list);
		result.setMeta(page);
		result.setErrorcode(errorCode);
		
		return result;
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
	public Object queryContractByIdOrDate(String orderno, String startTime,String endTime,HttpServletRequest request, HttpServletResponse response, String currentPage){
		Map<String, Object> map = null;
		Result result = Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long sellerId = upi.getId();
		
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		
		map = sellerContractService.queryContractByIdOrDate(currentPage, currentPage, currentPage, currentPage, sellerId);
		return result;
	} 
}
