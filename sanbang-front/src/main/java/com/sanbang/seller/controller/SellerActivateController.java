package com.sanbang.seller.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.bean.ezs_area;
import com.sanbang.bean.ezs_companyType_dict;
import com.sanbang.bean.ezs_industry_dict;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_companyType_dictMapper;
import com.sanbang.dao.ezs_industry_dictMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerActivateService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;

@Controller
@RequestMapping("/seller")
public class SellerActivateController {
	
	@Autowired
	private SellerActivateService activateService;
	
	@Autowired
	private DictService dictService;
	
	@Autowired
	private ezs_areaMapper ezs_areaMapper;
	
	@Autowired
	private ezs_industry_dictMapper ezs_industry_dictMapper;
	
	@Autowired
	private ezs_companyType_dictMapper ezs_companyType_dictMapper;
	
	@Autowired
	private ezs_userMapper ezs_userMapper;
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
			String employee_num, String assets, String obtainYear, HttpServletRequest request, HttpServletResponse response){
		Result result=Result.failure();
		
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setSuccess(false);
			result.setMsg("请重新登陆！");
			return result;
		}
		
		result = activateService.addActivateInfo(result, upi,companyName, yTurnover, covered, rent, device_num, employee_num, assets, 
				obtainYear, request, response);
		return result;
	}
	
	/**
	 * 供应商激活未通过时，调用此接口回显激活信息
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
		
		ezs_store store = upi.getEzs_store();
		
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
	

	/**
	 * 供应商激活信息初始化
	 * @param 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/sellerActiviteInit")
	@ResponseBody
	public Object sellerActiviteInit(HttpServletRequest request) throws Exception{
		Result result=Result.failure();
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		if(null!=upi.getEzs_userinfo()){
			Integer status = upi.getEzs_store().getStatus();
			String userType = upi.getEzs_store().getUserType();
			result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			result.setSuccess(true);
			result.setMsg("请求成功");
			Map<String, Object> map=new HashMap<>();
			Map<String,Object> map1=new HashMap<>();
			map1.put("status", status);
			map1.put("userType", userType);
			map1.put("companyName", upi.getEzs_store().getCompanyName());// 企业名称
			List<ezs_companyType_dict>  aa=	ezs_companyType_dictMapper.getCompanyTypeByThisId(upi.getEzs_store().getId());
			map1.put("yTurnover", upi.getEzs_store().getyTurnover());//// 年营业额
			map1.put("covered", upi.getEzs_store().getCovered());// 占地面积
			map1.put("rent", upi.getEzs_store().getRent());// 租用
			map1.put("device_num", upi.getEzs_store().getDevice_num());// 设备数量
			map1.put("employee_num", upi.getEzs_store().getEmployee_num());// 员工数量
			map1.put("fixed_assets", upi.getEzs_store().getFixed_assets());// 固定资产
			map1.put("obtainYear", upi.getEzs_store().getObtainYear());// 实际控制人从业年限
			map1.put("assets", upi.getEzs_store().getAssets());// 总资产
			map.put("cominfo", map1);
 			result.setObj(map);
 			if ("BUYER".equals(userType)) {
 				switch (status) {
				case 0:
					result.setSuccess(true);
					result.setMsg("请求成功");
					break;
				case 1:
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("实名认证待审核");
					break;
				case 2:
					result.setSuccess(true);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					result.setMsg("请求成功");
					break;
				case 3:
					result.setSuccess(false);
					result.setMsg("实名认证审核未通过");
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					break;
					
				default:
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
					result.setMsg("系统错误");
					break;
				}
 				return result;
			}
		};
		
		return result;
	}
}
