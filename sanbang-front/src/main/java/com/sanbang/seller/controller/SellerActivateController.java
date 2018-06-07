package com.sanbang.seller.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerActivateService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerActivateController {
	
	@Autowired
	SellerActivateService activateService;
	
	@Autowired
	DictService dictService;
	
	/**
	 * 供应商激活
	 * @param companyName
	 * @param yTurnover
	 * @param covered
	 * @param rent
	 * @param device_num
	 * @param employee_num
	 * @param assets
	 * @param open_bank_name
	 * @param openBankNo
	 * @param open_branch_name
	 * @param open_branch_no
	 * @param location_detail
	 * @return
	 */
	@RequestMapping("/sellerActivate") // 固定产值，经营年限  找不到对应字段
	@ResponseBody
	public Object sellerActivate(String companyName, String yTurnover, String covered, String rent, String device_num,
			String employee_num, String assets, String obtainYear,String open_bank_name, String openBankNo, String open_branch_name,
			String open_branch_no, String location_detail, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setSuccess(false);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		//验证用户是否认证，拥有买家资质
//		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_AUTHENTICATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未认证，不能申请卖家权限。");
//			return result;
//		}
		
		result = activateService.addActivateInfo(result, upi,companyName, yTurnover, covered, rent, device_num, employee_num, assets, 
				obtainYear, open_bank_name, openBankNo, open_branch_name, open_branch_no, location_detail, request, response);
		return result;
	}
	
	/**
	 * 供应商激活未通过时，调用此接口修改激活信息，再次提交
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateSellerInfo") // 固定产值，经营年限  找不到对应字段
	@ResponseBody
	public Object updateSellerInfo(HttpServletRequest request, HttpServletResponse response){
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		Result result=Result.failure();
		Map<String, Object> map = new HashMap<>();
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setSuccess(false);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		//验证用户是否认证，拥有买家资质
		ezs_store store = upi.getEzs_store();
//		Integer storeStatus = store.getStatus();
//		Long auditingusertype_id = store.getAuditingusertype_id();
//		String dictCode = dictService.getCodeByAuditingId(auditingusertype_id);
//		if (!(storeStatus == 2 && DictionaryCate.CRM_USR_TYPE_AUTHENTICATION.equals(dictCode))) {
//			result.setSuccess(false);
//			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
//			result.setMsg("用户未认证，不能申请卖家权限。");
//			return result;
//		}
		
		if (store.getStatus() != 3) {
			result.setSuccess(false);
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setMsg("用户状态不是审核未通过，不能修改激活信息。");
			return result;
		}
		
		map.put("companyName",  store.getCompanyName());
		map.put("yTurnover", store.getyTurnover());
		map.put("covered", store.getCovered());
		map.put("device_num", store.getDevice_num());
		map.put("employee_num", store.getEmployee_num());
		map.put("assets", store.getAssets());
		map.put("obtainYear", store.getObtainYear());
		map.put("open_bank_name", store.getOpen_bank_name());
		map.put("openBankNo", store.getOpenBankNo());
		map.put("open_branch_no", store.getOpen_branch_no());
		map.put("open_branch_name", store.getOpen_branch_name());
		map.put("location_detail", store.getLocation_detail());
		map.put("rent", store.getRent());
		
		
		result.setObj(map);
		return result;
	}
	
	
	
	
}
