package com.sanbang.buyer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_customized;
import com.sanbang.buyer.service.PurchaseService;
import com.sanbang.dao.ezs_customizedMapper;
import com.sanbang.dao.ezs_customized_recordMapper;
import com.sanbang.dao.ezs_customized_resMapper;
import com.sanbang.vo.DictionaryCode;

@Service
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	private ezs_customizedMapper customizedMapper;
	@Autowired
	private ezs_customized_recordMapper customizedRecordMapper;
	@Autowired
	private ezs_customized_resMapper customizedResMapper;
	
	@Override
	public Map<String, Object> getPurchaseGoodsByUser(Long userId) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_customized> elist = null;
		try {
			elist = this.customizedMapper.getPurchaseByUserId(userId);
			mmp.put("Obj", elist);
			mmp.put("Msg", "响应成功");
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
		} catch (Exception e) {
			// TODO: handle exception\
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "响应失败");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> getPurchaseById(Long Id) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		ezs_customized customized = null;
		try{
			customized = this.customizedMapper.selectByPrimaryKey(Id);
			mmp.put("Obj", customized);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "响应成功");
		}catch(Exception e){
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "响应失败");
		}
		return mmp;
	}

	@Override
	public Map<String, Object> submitOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> submitQuestion(String Msg) {
		// TODO Auto-generated method stub
		return null;
	}

}
