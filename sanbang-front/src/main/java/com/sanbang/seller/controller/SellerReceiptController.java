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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerReceiptService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerReceiptController {
	
	//日志
	private Logger log=Logger.getLogger(SellerReceiptController.class);
	
	@Autowired
	SellerReceiptService sellerReceiptService;
	
	@Autowired
	DictService dictService;
	
	@Autowired
	private BuyerService buyerService;
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
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
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
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("查询失败");
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
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
	
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
	 * 票据详情页面(发票查看)
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
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
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
	
	
	/**
	 * 查看合同
	 * 
	 * @param order_no
	 * @param request
	 * @return
	 */
//	@RequestMapping("/showordercontent")
//	@ResponseBody
//	public Result showOrderContent(@RequestParam(name = "order_no", defaultValue = "") String order_no,
//			HttpServletRequest request) {
//
//		Result result = Result.success();
//		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
//		result.setMsg("请求成功");
//		try {
//			result = buyerService.showOrderContent(request, order_no);
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.setSuccess(false);
//			result.setMsg("查看合同失败");
//			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
//		}
//		return result;
//	}

	
	
	
	/**
	 * 获取合同列表
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getContentList")
	@ResponseBody
	public Result getContentList(@RequestParam(name = "pageno", defaultValue = "1") int pageno,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		
		ezs_user upi = RedisUserSession.getLoginUserInfo(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未激活，没有卖家权限。");
//			return result;
//		}
		
		try {
			//采购合同 5，销售合同 6
			result = buyerService.getContentList(upi.getEzs_store().getNumber(), 5, pageno, request);
		} catch (Exception e) {
			result.setMsg("未获取到数据");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			e.printStackTrace();
		}
		return result;
	}
	
}

