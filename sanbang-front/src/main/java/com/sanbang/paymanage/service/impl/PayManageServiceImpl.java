package com.sanbang.paymanage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_payinfo;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.paymanage.service.PayManageService;
import com.sanbang.utils.Result;

@Service("payManageService")
public class PayManageServiceImpl implements PayManageService{
	
	
	@Autowired
	private ezs_payinfoMapper ezs_payinfoMapper;

	@Override
	public Result selectPayInfoByParam(Map<String, Object> map) {
		
		List<ezs_payinfo> payinfoRecord = ezs_payinfoMapper.selectPayRecord(map);
		List<ezs_payinfo> payinfoRecordTotal = ezs_payinfoMapper.selectPayRecordTotal(map);
		
		Result result = new Result().success();
		map.clear();
		map.put("recored",payinfoRecord);
		map.put("recordTotal", payinfoRecordTotal);
		
		result.setObj(map);
		
		return result;
	}
	
	

}
