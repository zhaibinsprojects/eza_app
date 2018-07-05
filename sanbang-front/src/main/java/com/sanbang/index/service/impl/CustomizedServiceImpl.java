package com.sanbang.index.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_customizedMapper;
import com.sanbang.dao.ezs_customized_recordMapper;
import com.sanbang.index.service.CustomizedService;
import com.sanbang.vo.DictionaryCode;

@Service
public class CustomizedServiceImpl implements CustomizedService {
	@Autowired
	private ezs_customizedMapper customizedMapper;
	@Autowired
	private ezs_customized_recordMapper customizedRecordMapper; 
	
	@Override
	public int insert(ezs_customized record) {
		return this.customizedMapper.insert(record);
	}

	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public Map<String, Object> addCustomized(ezs_user user, ezs_customized customized,
			ezs_customized_record customizedRecord) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		try {
			//初始化必需字段数据
			customized.setAddTime(new Date());
			customized.setPurchaser_id(user.getId());
			customized.setDeleteStatus(false);
			customized.setStatus("0");
			customized.setSource_type("1");//来源类型:0是预购  1是采购
			this.customizedMapper.insert(customized);
			//初始化必需字段数据
			customizedRecord.setRemark("您的需求已经提交成功");
			customizedRecord.setCustomized_id(customized.getId());
			customizedRecord.setAddTime(new Date());
			customizedRecord.setOperater_id(user.getId());
			customizedRecord.setPurchaser_id(user.getId());
			customizedRecord.setDeleteStatus(false);
			this.customizedRecordMapper.insert(customizedRecord);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "定制采购添加成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			throw e;
		}
		return mmp;
	}
}
