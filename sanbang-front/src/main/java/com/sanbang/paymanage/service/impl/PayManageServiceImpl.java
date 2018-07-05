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
		String s1 = "";
		String s2 = "";
		for(ezs_payinfo einfo:payinfoRecordTotal){
			if(einfo.getOrder_type()==1){//采购单，为支出
				 s1 = "已支出"+einfo.getAcount()+"笔共"+einfo.getAprice()+"元,";
			}
			if(einfo.getOrder_type()==2){
				 s2 = "已收入"+einfo.getAcount()+"笔共"+einfo.getAprice()+"元";
			}
		}
		if("".equals(s1)){
			 s1 = "已支出0笔共0元,";
		}
		if("".equals(s2)){
			s2 = "已收入0笔共0元";
		}
		Result result = Result.success();
		map.clear();
		map.put("recored",payinfoRecord);
		map.put("recordTotal", s1+s2);
		
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
