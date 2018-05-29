package com.sanbang.buyer.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.OrderEvaluateService;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_dvaluateMapper;
import com.sanbang.vo.DictionaryCode;

@Service
public class OrderEvaluateServiceImpl implements OrderEvaluateService {
	@Autowired
	private ezs_dvaluateMapper dvaluateMapper; 
	@Autowired
	private ezs_accessoryMapper accessoryMapper; 

	@Override
	@Transactional
	public Map<String, Object> orderEvaluate(ezs_dvaluate dvaluate,ezs_accessory accessory,ezs_user user) {
		Map<String, Object> mmp = new HashMap<>();
		//图片信息
		accessory.setAddTime(new Date());
		accessory.setUser_id(user.getId());
		accessory.setDeleteStatus(false);
		//accessory.setSize();
		//accessory.setWidth();
		//评价信息
		dvaluate.setAddTime(new Date());
		dvaluate.setDeleteStatus(false);
		
		//dvaluate.setGoodQuality(goodQuality);
		//dvaluate.setLogistics(logistics);
		//dvaluate.setServiceQuality(serviceQuality);
		
		byte[] tByte = {user.getId().byteValue()};
		dvaluate.setUser(tByte);
		try {
			if(accessory.getName()!=null){
				this.accessoryMapper.insert(accessory);
				//dvaluate.setPicId(accessory.getId());
			}
			//在此添加图片的ID
			this.dvaluateMapper.insert(dvaluate);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "评价成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}

}
