package com.sanbang.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerReturnOrderService;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerReturnOrderService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

/**
 * 卖家中心退货
 * 
 * @author LENOVO
 *
 */
@Controller
@RequestMapping("/app/seller/returnOrder")
public class APPSellerReturnOrderController {

	@Autowired
	private SellerReturnOrderService sellerReturnOrderService;
	
	@Autowired
	DictService dictService;
	
	/**
	 * 申请退货订单列表
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("orderlist")
	public Result returnOrderList(HttpServletRequest request,
			@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(name = "order_type", defaultValue = "") int order_type,
			@RequestParam(name = "state2", defaultValue = "") int state2) {
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		result = sellerReturnOrderService.returnOrderList(pageNo, upi.getId(), order_type, state2);
		return result;
	}

	/**
	 * 申请退货订单详情
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("returnOrderinfo")
	public Result returnOrderinfoforBuyer(HttpServletRequest request,
			@RequestParam(name = "returnid", defaultValue = "1") int returnid) {
		Result result = Result.failure();
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		result = sellerReturnOrderService.returnOrderinfoforBuyer(returnid);
		return result;
	}

	/**
	 * 物流信息查看
	 * 
	 * @param order_no
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getreturnlogistics")
	public Result getreturnlogistics(@RequestParam(name = "returnid", defaultValue = "") long returnid,
			HttpServletRequest request, HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		try {
			result = sellerReturnOrderService.getreturnlogistics(returnid);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

	/**
	 * 退货上传退款凭证 returnurl
	 * 
	 * @param returnid
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/submitReturnPayInfo")
	public Result submitReturnPayInfo(@RequestParam(name = "returnid", defaultValue = "") long returnid,
			@RequestParam(name = "returnurl", defaultValue = "") String returnurl, HttpServletRequest request,
			HttpServletResponse response) {
		Result result = Result.success();
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		ezs_user upi = RedisUserSession.getUserInfoByKeyForApp(request);
		if (upi == null) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		//验证用户是否激活，拥有卖家权限
		ezs_store store = upi.getEzs_store();
		Integer storeStatus = store.getStatus();
		Long auditingusertype_id = store.getAuditingusertype_id();
		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_ACTIVATION.equals(dictCode))) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户未激活，没有卖家权限。");
			return result;
		}
		
		try {
			result = sellerReturnOrderService.submitReturnPayInfo(request, returnurl, returnid);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
	}

}
