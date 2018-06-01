package com.sanbang.paymanage.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_payinfo;
import com.sanbang.dao.ezs_payinfoMapper;
import com.sanbang.paymanage.service.PayManageService;
import com.sanbang.utils.Page;
import com.sanbang.utils.Result;

@Service("payManageService")
public class PayManageServiceImpl implements PayManageService{
	
	
	@Autowired
	private ezs_payinfoMapper ezs_payinfoMapper;

	@Override
	public Result selectPayInfoByParam(Map<String, Object> map,Page page) {	
		
		//交易详情
		List<ezs_payinfo> payinfoRecord = ezs_payinfoMapper.selectPayRecord(map);
		//交易详情记录数量
		int payinfoRecordCount = ezs_payinfoMapper.selectCount(map);
		page.setTotalCount(payinfoRecordCount);
		
		//交易记录
		List<ezs_payinfo> payinfoRecordTotal = ezs_payinfoMapper.selectPayRecordTotal(map);
		
		Result result = new Result().success();
		map.clear();
		map.put("recored",payinfoRecord);
		map.put("recordTotal", payinfoRecordTotal);
		
		result.setMeta(new Page(page.getPageNow(), page.getPageSize(), page.getTotalCount(), page.getTotalPageCount(), 0, true,
				true, true, true));
		if (payinfoRecord.size() == 0) {
			result.getMeta().setHasFirst(false);
		}
		if (page.getPageNow() == 1) {
			result.getMeta().setHasPre(false);
		}
		if(page.getPageNow()>=page.getTotalPageCount()){
			result.getMeta().setHasNext(false);
			result.getMeta().setHasLast(false);
		}
		
		result.setObj(map);
		
		return result;
	}
	
	

}
