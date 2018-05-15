package com.sanbang.seller.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_pactMapper;
import com.sanbang.seller.service.SellerContractService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;
@Service
public class SellerContractServiceImpl implements SellerContractService {
	
	//日志
	private static Logger log = Logger.getLogger(SellerContractServiceImpl.class.getName());
	
	@Autowired
	ezs_pactMapper ezs_pactMapper;
	
	/**
	 * 供应商合同列表展示
	 */
	@Override
	public Object contractManage(Result result, HttpServletRequest request, HttpServletResponse response) {
		log.info("合同管理**************************");
		
		List<ezs_pact> list = new ArrayList<>();
		
		ezs_user user = RedisUserSession.getLoginUserInfo(request);
		if (user != null) {
			Long sellerUser_id  = user.getId();
			
			try {
				list = ezs_pactMapper.selectPactBySellerId(sellerUser_id);
			} catch (Exception e) {
				log.info("查询合同信息" + e.toString());
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_LIST_FAIL);
				result.setSuccess(false);
				result.setMsg("系统错误");
				return result;
			}
			if (list.size()>0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_INFO_SUCCESS);
				result.setSuccess(true);
				result.setMsg("合同信息查询成功");
				return list;
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_LIST_FAIL);
				result.setSuccess(false);
				result.setMsg("合同信息查询失败");
				return result;
			}
		}else {
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_LIST_FAIL);
			result.setSuccess(false);
			result.setMsg("系统错误");
			return result;
		}
	}
	/**
	 * 供应商合同详情查看
	 */
	@Override
	public Object queryContractInfo(Long pactId, Result result, HttpServletRequest request, HttpServletResponse response) {
		log.info("合同详情**************************");
		ezs_pact pact = null;
		try {
			pact = ezs_pactMapper.selectByPrimaryKey(pactId);
		} catch (Exception e) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_INFO_FAIL);
			result.setSuccess(false);
			result.setMsg("系统错误");
			return result;
		}
		
		if (pact != null ) {
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_INFO_SUCCESS);
			result.setSuccess(true);
			result.setMsg("查询合同详情成功");
			return pact;
		}else{
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_INFO_FAIL);
			result.setSuccess(false);
			result.setMsg("查询合同详情失败");
			return result;
		}
	}
	@Override
	public Object queryContractByIdAndDate(Result result, String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("条件查询合同**************************");
		List<ezs_pact> list = new ArrayList<>();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		
		try {
			Date dt1 = df.parse(startTime);
			Date dt2 = df.parse(endTime);
			if (dt1.getTime() > dt2.getTime()) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
				result.setSuccess(false);
				result.setMsg("开始时间不能晚于结束时间");
			    return result;
			}
			
			list = ezs_pactMapper.selectPactContractByIdAndDate(orderno,startTime,endTime);
			
			
			if (list.size() < 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_INFO_FAIL);
				result.setSuccess(false);
				result.setMsg("系统错误");
				return result;
			}
			if (list.size() == 0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_INFO_SUCCESS);
				result.setSuccess(true);
				result.setMsg("没有符合条件的合同");
				return result;
			}
			
			
		}catch (Exception e) {
			log.info("条件查询合同信息失败" + e.toString());
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_LIST_FAIL);
			result.setSuccess(false);
			result.setMsg("系统错误");
			return result;
		}
		
		
		return list;
	}

}
