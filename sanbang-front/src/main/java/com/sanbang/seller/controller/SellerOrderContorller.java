package com.sanbang.seller.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_logistics;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.seller.service.SellerOrderService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerOrderContorller {
	
	//日志
	private static Logger log = Logger.getLogger(SellerOrderContorller.class.getName());
	
	@Autowired
	SellerOrderService sellerOrderService;
	
	
	
	/**
	 * 分页查询订单列表
	 * @param orderType
	 * @param orderStatus
	 * @param currentPage
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryOrderList")
	public Object queryOrderList(String orderType, Integer orderStatus, String currentPage, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long userId = upi.getId();

		Map<String, Object> mmp = null;
		Page page = null;
		if(currentPage==null){
			currentPage = "1";
		}
		mmp = sellerOrderService.queryOrders(currentPage, userId, orderType, orderStatus);
		
		List<ezs_purchase_orderform> list = new ArrayList<>();
		
		list = (List<ezs_purchase_orderform>)mmp.get("Obj");
		
		int errorCode = (int) mmp.get("ErrorCode");
		
		page = (Page) mmp.get("page");
		result.setObj(list);
		result.setMeta(page);
		result.setErrorcode(errorCode);
		
		return result;
	}
	
	@RequestMapping("/queryOrderInfoById")
	public Object queryOrderInfoById(long orderId, HttpServletRequest request){
		Result result = Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		ezs_purchase_orderform  purchaseOrder;
		try {
			purchaseOrder = sellerOrderService.queryOrderInfoById(orderId);
			if (purchaseOrder != null ) {
				
				result.setSuccess(true);
				result.setMsg("查询成功");
				result.setObj(purchaseOrder);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("查询失败");
			}
			
		} catch (Exception e) {
			log.info("查询订单消息出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return result;
	}
	
//	/**
//	 * 样品发货
//	 * @param orderId
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("/toOrderDeliver")
//	public Object toOrderDeliver(long orderId, HttpServletRequest request, HttpServletResponse response){
//		Result result = Result.failure();
//		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
//		if(upi==null){
//			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
//			result.setMsg("用户未登录");
//			return result;
//		}
//		Map<String, Object> map = new HashMap<>();
//		ezs_purchase_orderform  purchaseOrder;
//		try {
//			purchaseOrder = sellerOrderService.queryOrderInfoById(orderId);
//			if (purchaseOrder != null ) {
//				
//				
//				
//				
//				result.setSuccess(true);
//				result.setMsg("查询成功");
//				result.setObj(purchaseOrder);
//			}else{
//				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//				result.setSuccess(false);
//				result.setMsg("查询失败");
//			}
//			
//		} catch (Exception e) {
//			log.info("注册：保存用户注册信息错误" + e.toString());
//			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
//			result.setSuccess(false);
//			result.setMsg("系统错误");
//		}
//		return orderId;
//	}
	
	/**
	 * 发票上传
	 * @param orderId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/upLoadInvoice")
	public Object upLoadInvoice(String orderId, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long userId = upi.getId();
		
		ezs_invoice invoice = new ezs_invoice();
		
		
		
		return response;
	}
	/**
	 * 查看发票信息
	 * @param orderNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryInvoiceInfoById")
	public Object queryInvoiceInfoById(String orderNo, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		ezs_invoice invoice = null; 
				
		try {
			invoice = sellerOrderService.queryInvoiceByNo(orderNo);
			if (invoice != null ) {
				
				result.setSuccess(true);
				result.setMsg("查询成功");
				result.setObj(invoice);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("查询失败");
			}
			
		} catch (Exception e) {
			log.info("查询发票信息出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return result;
	}
	
	@RequestMapping("/queryLogisticsInfoById")
	public Object queryLogisticsInfoById(String orderNo, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		ezs_logistics logistics = null; 
		try {
			logistics = sellerOrderService.queryLogisticsByNo(orderNo);
			if (logistics != null ) {
				result.setSuccess(true);
				result.setMsg("查询成功");
				result.setObj(logistics);
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("查询失败");
			}
		} catch (Exception e) {
			log.info("查询物流信息出错" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return upi;
	}
}
