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
import com.sanbang.redis.RedisResult;
import com.sanbang.seller.service.SellerContractService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
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
		
//		ezs_user user = RedisUserSession.getLoginUserInfo(request);
//		RedisResult<ezs_user> tempCached=(RedisResult<ezs_user>) RedisUtils.get(userKey,ezs_user.class);
		
		ezs_user user = new ezs_user();
		user.setId((long) 2);
		if (user != null) {
			Long sellerId  = user.getId();
			
			try {
				list = ezs_pactMapper.selectPactBySellerId(sellerId);
			
			} catch (Exception e) {
				log.info("查询合同信息" + e.toString());
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_LIST_FAIL);
				result.setSuccess(false);
				result.setMsg("系统错误");
			}
			if (list.size()>0) {
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_INFO_SUCCESS);
				result.setSuccess(true);
				result.setMsg("合同信息查询成功");
			}else{
				result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_LIST_FAIL);
				result.setSuccess(false);
				result.setMsg("合同信息查询失败");
			}
		}else {
			result.setErrorcode(DictionaryCode.ERROR_WEB_QUERY_CONTRACT_LIST_FAIL);
			result.setSuccess(false);
			result.setMsg("系统错误");
		}
		return result;
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
	
	/**
	 * 条件查询合同（根据订单编号，或者签订时间查询）
	 */
	@Override
	public Object queryContractByIdOrDate(String orderno, String startTime, String endTime,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("条件查询合同**************************");
		
		List<ezs_pact> list = new ArrayList<>();
		Result result = Result.failure();
		if(Tools.notEmpty(orderno) && Tools.isNum(orderno)){
			list = ezs_pactMapper.selectPactByOrderNo(orderno);
			result.setObj(list);
		}
		if (Tools.notEmpty(startTime) && Tools.notEmpty(endTime)){
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date dt1;
			Date dt2;
			try {
				dt1 = df.parse(startTime);
				dt2 = df.parse(endTime);
				if (dt1.getTime() > dt2.getTime()) {
					result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
					result.setMsg("日期格式错误");
					return result;
				}
				list = ezs_pactMapper.selectPactByDate(dt1, dt2);
				result.setObj(list);
				
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
		}
		return result;
	}

}
