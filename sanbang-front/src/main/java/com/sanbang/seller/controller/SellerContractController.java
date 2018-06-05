package com.sanbang.seller.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.BuyerService;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerContractService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerContractController {
	
	@Autowired
	SellerContractService sellerContractService;
	
	@Autowired
	private BuyerService buyerService;
	
	@Autowired
	DictService dictService;
	
	/**
	 * 合同管理，展示合同列表
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping("/contractManage")
//	@ResponseBody
//	public Object contractManage(HttpServletRequest request, HttpServletResponse response, String currentPage){
//		Map<String, Object> map = null;
//		Result result = Result.failure();
//		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
//		if(upi==null){
//			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
//			result.setMsg("用户未登录");
//			return result;
//		}
//		Long sellerId = upi.getId();
//		
//		//验证用户是否激活，拥有卖家权限
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
//		
//		Page page = null;
//		if(currentPage==null){
//			currentPage = "1";
//		}
//		map = sellerContractService.contractManage(currentPage, sellerId);
//		List<ezs_pact> list = new ArrayList<>();
//		
//		list = (List<ezs_pact>)map.get("Obj");
//		
//		int errorCode = (int) map.get("ErrorCode");
//		
//		page = (Page) map.get("page");
//		result.setObj(list);
//		result.setMeta(page);
//		result.setErrorcode(errorCode);
//		
//		return result;
//	}
	/**
	 * 查看合同详情
	 * @param pactId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryContractInfo")
	@ResponseBody
	public Object queryContractInfo(@RequestParam(name = "order_no", defaultValue = "") String order_no,
			HttpServletRequest request) {

		Result result = Result.success();
		
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		Long sellerId = upi.getId();
		
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
		result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		result.setMsg("请求成功");
		try {
			result = buyerService.showOrderContent(request, order_no);
		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("查看合同失败");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
		}
		return result;
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
	@SuppressWarnings("unchecked")
	@RequestMapping("/queryContractByIdOrDate")
	@ResponseBody
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
		
		map = sellerContractService.queryContractByIdOrDate(orderno, startTime, endTime, currentPage, sellerId);
		
		List<ezs_pact> list = new ArrayList<>();
		
		list = (List<ezs_pact>)map.get("Obj");
		
		int errorCode = (int) map.get("ErrorCode");
		
		page = (Page) map.get("page");
		result.setObj(list);
		result.setMeta(page);
		result.setErrorcode(errorCode);
		
		return result;
	} 
}
