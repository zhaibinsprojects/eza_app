package com.sanbang.seller.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_user;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerReceiptController {
	
	//日志
	private Logger log=Logger.getLogger(SellerReceiptController.class);
	
	@Autowired
	SellerReceiptService sellerReceiptService;
	
	/**
	 * 票据管理页面,展示列表
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getReceiptList")
	@ResponseBody
	public Object getReceiptList(HttpServletRequest request, HttpServletResponse response, String currentPage){
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
	@ResponseBody
	public Object queryInvoiceByIdOrDate(String orderno, String startTime,String endTime,HttpServletRequest request, HttpServletResponse response,String currentPage){
		Map<String, Object> map = new HashMap<>();
		Result result=Result.failure();
		List<ezs_invoice> list = null;
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
	 * 票据详情页面
	 * @param orderNo
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryInvoiceInfoById")
	@ResponseBody
	public Object queryInvoiceInfoById(String orderNo, HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		Map<String, Object> map = new HashMap<>();
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long userId = upi.getId();
		
		ezs_invoice invoice = null; 
		ezs_accessory accessory = null;		
		try {
			invoice = sellerReceiptService.queryInvoiceByNo(orderNo);
			if (invoice != null ) {
				
				Long receipt_id = invoice.getReceipt_id();
				accessory = sellerReceiptService.queryAccessoryById(receipt_id);
				String path = accessory.getPath();
				map.put("invoice", invoice);
				map.put("path", path);
				result.setSuccess(true);
				result.setMsg("查询成功");
				result.setObj(map);
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
	
}

