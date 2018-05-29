package com.sanbang.buyer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_dvaluate_accessroy;
import com.sanbang.bean.ezs_user;
import com.sanbang.buyer.service.OrderEvaluateService;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.dao.ezs_dvaluateMapper;
import com.sanbang.dao.ezs_dvaluate_accessroyMapper;
import com.sanbang.vo.DictionaryCode;

@Service
public class OrderEvaluateServiceImpl implements OrderEvaluateService {
	@Autowired
	private ezs_dvaluateMapper dvaluateMapper; 
	@Autowired
	private ezs_accessoryMapper accessoryMapper;
	@Autowired
	private ezs_dvaluate_accessroyMapper dvaluateAccessroyMapper;

	@Override
	@Transactional
	public Map<String, Object> orderEvaluate(ezs_dvaluate dvaluate,List<ezs_accessory> aList,ezs_user user) {
		Map<String, Object> mmp = new HashMap<>();
		List<ezs_dvaluate_accessroy> dList = new ArrayList<>(); 
		//图片信息
		for (ezs_accessory accessory : aList) {
			accessory.setAddTime(new Date());
			accessory.setUser_id(user.getId());
			accessory.setDeleteStatus(false);
		}
		//评价信息
		dvaluate.setAddTime(new Date());
		dvaluate.setDeleteStatus(false);
		byte[] tByte = {user.getId().byteValue()};
		dvaluate.setUser(tByte);
		try {
			for (ezs_accessory accessory : aList) {
				if(accessory.getName()!=null){
					ezs_dvaluate_accessroy dvaluateAccessroy = new ezs_dvaluate_accessroy();
					this.accessoryMapper.insert(accessory);
					dvaluateAccessroy.setAccessroy_id(accessory.getId());
					dList.add(dvaluateAccessroy);
				}
				//在此添加图片的ID
				this.dvaluateMapper.insert(dvaluate);
				for (ezs_dvaluate_accessroy daccessroy : dList) {
					daccessroy.setDvaluate_id(dvaluate.getId());
					this.dvaluateAccessroyMapper.insert(daccessroy);
				}
			}
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
