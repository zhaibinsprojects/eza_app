package com.sanbang.seller.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.dict.service.DictService;
import com.sanbang.seller.service.SellerActivateService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCate;
import com.sanbang.vo.DictionaryCode;
@Service
public class SellerActivateServiceImpl implements SellerActivateService {
	//日志
	private static Logger log = Logger.getLogger(SellerActivateServiceImpl.class.getName());
	
	@Autowired
	private ezs_storeMapper ezs_storeMapper;
	
	// 用户登陆信息
	@Resource(name = "ezs_userMapper")
	private ezs_userMapper ezs_userMapper;
	
	@Resource(name = "dictService")
	private DictService dictService;
	
	// rediskey有效期
	@Value("${consparam.redis.redisuserkeyexpir}")
	private String redisuserkeyexpir;
	
	@Override
	public Result addActivateInfo(Result result, ezs_user upi,String companyName, String yTurnover, String covered, String rent,
			String device_num, String employee_num, String assets, String obtainYear, HttpServletRequest request, HttpServletResponse response) {
		log.info("供应商激活参数：companyName" + companyName + "&yTurnover" + yTurnover + "covered" +"。。。。。。。。。。。。。。。。");
		//校验
		String fixed_assets=request.getParameter("fixed_assets");
		
		result = checkActivateInfo(result, companyName, yTurnover, covered, rent,
				device_num, employee_num, assets, obtainYear,fixed_assets, request, response);
		if (!result.getSuccess()) {
			return result;
		}
		if (upi != null) {
			Integer status = upi.getEzs_store().getStatus();
			String userType = upi.getEzs_store().getUserType();
			if ("BUYER".equals(userType)) {
 				switch (status) {
				case 0:
					result.setSuccess(false);
					result.setMsg("请先进行实名认证");
					return result;
				case 1:
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("实名认证待审核");
					return result;
				case 2:
					result.setSuccess(true);
					result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
					result.setMsg("请求成功");
					break;
				case 3:
					result.setSuccess(false);
					result.setMsg("实名认证审核未通过");
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					return result;
				default:
					result.setSuccess(false);
					result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
					result.setMsg("系统错误");
					return result;
				}
 				if(!result.getSuccess()){
 					return result;
 				}
			}
			Long store_id = upi.getStore_id();
			ezs_store store = ezs_storeMapper.selectByPrimaryKey(store_id);
			store.setCompanyName(companyName);
			store.setyTurnover(Double.valueOf(yTurnover));
			store.setCovered(Double.valueOf(covered));
			store.setDevice_num(Integer.valueOf(device_num));
			store.setEmployee_num(Integer.valueOf(employee_num));
			store.setAssets(Double.valueOf(assets));
			store.setObtainYear(Integer.valueOf(obtainYear));
			store.setFixed_assets(Double.valueOf(fixed_assets));
			/*store.setOpen_bank_name(open_bank_name);
			store.setOpenBankNo(openBankNo);
			store.setOpen_branch_name(open_branch_name);
			store.setOpen_branch_no(open_branch_no);
			store.setLocation_detail(location_detail);*/
			store.setAuditingusertype_id(dictService.getDictById(DictionaryCate.CRM_USR_TYPE_ACTIVATION).getId());
			store.setUserType("SELLER");
			store.setStatus(1); // 0 初始值， 1 待审核 ， 2 审核通过， 3 审核不通过
			if (Boolean.valueOf(rent)) {
				store.setRent(true);//非租用
			}else{
				store.setRent(false);//租用
			}
			
			ezs_user ezs_user = new ezs_user();
			ezs_user.setUserRole("SELLER");
			ezs_user.setId(upi.getId());
			
			int aa = 0; 
			
			try {
				aa = ezs_storeMapper.updateByPrimaryKeySelective(store);
				ezs_userMapper.updateByPrimaryKeySelective(ezs_user);
				if (aa >0) {
					result.setErrorcode(DictionaryCode.ERROR_WEB_ACTIVATE_INFO_SUCCESS);
					result.setSuccess(true);
					result.setMsg("信息提交成功，请等待审核");
					
					if(result.getSuccess()){
						ezs_user upi1=ezs_userMapper.getUserInfoByUserNameFromBack(upi.getName()).get(0);
						boolean res=RedisUserSession.updateUserInfo(upi.getUserkey(), upi1, Long.parseLong(redisuserkeyexpir));
						if (res) {
							result.setSuccess(true);
							result.setMsg("保存成功");
							result.setErrorcode(DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						} else {
							result.setSuccess(false);
							result.setMsg("系统错误");
							result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
						}
					}
					
				}else{
					result.setErrorcode(DictionaryCode.ERROR_WEB_ACTIVATE_INFO_FAIL);
					result.setSuccess(false);
					result.setMsg("信息提交失败");
				}
			} catch (Exception e) {
				log.info("保存供应商激活信息失败" + e.toString());
				result.setErrorcode(DictionaryCode.ERROR_WEB_ACTIVATE_INFO_FAIL);
				result.setSuccess(false);
				result.setMsg("系统错误");
			}
			
			
		}else{
			result.setErrorcode(DictionaryCode.ERROR_WEB_ACTIVATE_INFO_FAIL);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return result;
	}

	private Result checkActivateInfo(Result result, String companyName, String yTurnover, String covered, String rent,
			String device_num, String employee_num, String assets, String obtainYear,String fixed_assets, HttpServletRequest request,
			HttpServletResponse response) {
		
		if (Tools.isEmpty(companyName)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("公司名称不能为空");
			return result;
		}

		if (Tools.isEmpty(yTurnover)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("年营业额不能为空");
			return result;
		}
		if (Tools.isEmpty(covered) || !Tools.isNum(covered)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("场地面积不能为空或非数字格式");
			return result;
		}
		if (Tools.isEmpty(device_num) || !Tools.isNum(device_num)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("设备数量不能为空或非数字格式");
			return result;
		}
		if (Tools.isEmpty(device_num) || !Tools.isNum(device_num)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("设备数量不能为空或非数字格式");
			return result;
		}
		
		if (Tools.isEmpty(employee_num) || !Tools.isNum(employee_num)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("工人人数不能为空或非数字格式");
			return result;
		}
		if (Tools.isEmpty(assets) || !Tools.isNum(assets)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("总资产不能为空或非数字格式");
			return result;
		}
		if (Tools.isEmpty(assets) || !Tools.isNum(assets)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("总资产不能为空或非数字格式");
			return result;
		}
		if (Tools.isEmpty(obtainYear) || !Tools.isNum(obtainYear)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("实际控制人从业年限不能为空或非数字格式");
			return result;
		}
		if (Tools.isEmpty(fixed_assets)) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("固定资产不能为空");
			return result;
		}
		if(Tools.isEmpty(rent) ){
			result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
			result.setSuccess(false);
			result.setMsg("场地是否租用不能为空");
			return result;
		}
		result.setSuccess(true);
		return result;
	}
}
