package com.sanbang.seller.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.seller.service.SellerReceiptService;
<<<<<<< HEAD
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsInfo;

@Controller
@RequestMapping("/seller")
public class SellerReceiptController {
	
	@Autowired
	SellerReceiptService sellerReceiptService;
	
	@Autowired
	ezs_accessoryMapper accessoryMapper;
	/**
	 * 票据管理页面
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getReceiptInfo")
	public Object getReceiptInfo(HttpServletRequest request, HttpServletResponse response, String currentPage){
		Map<String, Object> map = new HashMap<>();
		List<ezs_invoice> list = null;
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		Long userId = upi.getId();
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		map = sellerReceiptService.getInvoiceListById(userId,currentPage);
		Integer ErrorCode = (Integer)map.get("ErrorCode");
		if(ErrorCode!=null&&ErrorCode.equals(DictionaryCode.ERROR_WEB_REQ_SUCCESS)){
			list = (List<ezs_invoice>) map.get("Obj");
			page = (Page) map.get("Page");
			result = Result.success(); 
			result.setObj(list);
			result.setMeta(page);
		}else{
			result = Result.failure();
			result.setErrorcode(Integer.valueOf(map.get("ErrorCode").toString()));
			result.setMsg(map.get("Msg").toString());
		}
		return result;
	}
	
	/**
	 * 根据订单编号和时间查询发票
	 * @param orderno
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryInvoiceByIdOrDate")
	public Object queryInvoiceByIdOrDate(String orderno, String startTime,String endTime,HttpServletRequest request, HttpServletResponse response,String currentPage){
		Map<String, Object> map = new HashMap<>();
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("请重新登陆！");
			return result;
		}
		Long userId = upi.getId();
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}

		map = sellerReceiptService.queryInvoiceByIdOrDate(result, orderno, startTime, endTime, userId, currentPage);
		return map;
  }
}