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
	
//	/**
//	 * 根据id
//	 * @param byerId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/getByerBillInfoById")
//	public Object getByerBillInfoById(String byerId, HttpServletRequest request, HttpServletResponse response){
//		Map<String, Object> map = new HashMap<>();
//		
//		Result result=Result.failure();
//		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
//		if(upi==null){
//			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
//			result.setMsg("用户未登录");
//			return result;
//		}
//		
//		Long userId = upi.getId();
//		ezs_bill bill = sellerReceiptService.getBillInfoById(userId);
//		Long billId = bill.getId();
//		ezs_payinfo payInfo = sellerReceiptService.getPayInfoById(billId);
//		Long paymentUser_id = payInfo.getPaymentUser_id();
//		
//		ezs_user buyer = sellerReceiptService.getUserInfoById(paymentUser_id);
//		
//		
//		String companyName = bill.getCompanyName();
//		String dutyNo = bill.getDutyNo();
//		String address = bill.getAddress();
//		String phone = bill.getPhone();
//		String bank = bill.getBank();
//		String number = bill.getNumber();
//		
//		String name = buyer.getName();
//		String moblie = buyer.getPhone();
//		Long position_id = buyer.getPosition_id();
//		
//		map.put("companyName", companyName);
//		map.put("dutyNo", dutyNo);
//		map.put("address", address);
//		map.put("phone", phone);
//		map.put("bank", bank);
//		map.put("number", number);
//		map.put("name", name);
//		map.put("moblie", moblie);
//		map.put("position_id", position_id);
//
//		return map;
//	}
	/**
	 * 根据订单编号查询票据详情
	 * @param invoiceId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getBuyerInvoiceInfoById")
	public Object getBuyerInvoiceInfoById(String orderno, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> map = new HashMap<>();
		
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		ezs_invoice invoice = sellerReceiptService.getInvoiceInfoById(orderno);
		
		Long id = invoice.getId();
		Date express_time = invoice.getExpress_time();
		String express_name = invoice.getExpress_name();
		String express_no = invoice.getExpress_no();
		Integer invoice_status = invoice.getInvoice_status();
		Long receipt_id = invoice.getReceipt_id();
		
		ezs_accessory accessory = accessoryMapper.selectByPrimaryKey(receipt_id);
		String path = accessory.getPath();
		
		map.put("id", id);
		map.put("express_time", express_time);
		map.put("express_name", express_name);
		map.put("express_no", express_no);
		map.put("invoice_status", invoice_status);
		map.put("path", path);
		
		result.setObj(map);
		return result;
	}
}
