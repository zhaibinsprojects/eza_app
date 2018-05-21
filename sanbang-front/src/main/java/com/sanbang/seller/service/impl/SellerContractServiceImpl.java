package com.sanbang.seller.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_pact;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_pactMapper;
import com.sanbang.redis.RedisResult;
import com.sanbang.seller.service.SellerContractService;
import com.sanbang.utils.Page;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.RedisUtils;
import com.sanbang.utils.Result;
import com.sanbang.utils.Tools;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.HomeDictionaryCode;
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
	public Map<String, Object> contractManage(String currentPage, Long sellerId) {
		log.info("合同管理**************************");
		Map<String, Object> mmp = new HashMap<>();
		//获取总页数
		int totalCount =ezs_pactMapper.selectCountById(sellerId);
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		int startPos = 0;
		page.setStartPos(startPos);
		page.setPageSize(10);
		if(Integer.valueOf(currentPage)>=1||Integer.valueOf(currentPage)<=page.getTotalPageCount()){
			List<ezs_pact> list = this.ezs_pactMapper.queryPact(page, sellerId);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Page", page);
			mmp.put("Obj", list);
		}else{
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Page", page);
		}
		return mmp;
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
	public Map<String, Object> queryContractByIdOrDate(String orderno, String startTime, String endTime,
			String currentPage, Long sellerId) {
		log.info("条件查询合同**************************");
		Result result = Result.failure();
		Map<String, Object> map = new HashMap<>();
		List<ezs_pact> list = new ArrayList<>();
		//获取总页数
		int totalCount = ezs_pactMapper.selectCountById(sellerId);
		
		Page page = new Page(totalCount, Integer.valueOf(currentPage));
		page.setPageSize(10);
		int startPos = 0;
		page.setStartPos(startPos);
		if(Integer.valueOf(currentPage)>=1&&Integer.valueOf(currentPage)<=page.getTotalPageCount()){
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
					}
					list = ezs_pactMapper.selectPactByDate(dt1, dt2,sellerId,page);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				map.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				map.put("Page", page);
				map.put("Obj", list);
			}else{
				ezs_invoice invoice = ezs_pactMapper.selectPactByOrderNo(orderno);
				map.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				map.put("Page", page);
				map.put("Obj", invoice);
			}
		}else{
			map.put("ErrorCode", HomeDictionaryCode.ERROR_HOME_PAGE_FAIL);
			map.put("Msg", "页码越界");
			map.put("Page", page);
		}
		return map;
	}
	

}
