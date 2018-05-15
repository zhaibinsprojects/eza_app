package com.sanbang.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_customized_record;
import com.sanbang.dao.ezs_customized_recordMapper;
import com.sanbang.index.service.CustomizedRecordService;

@Service
public class CustomizedRecordServiceImpl implements CustomizedRecordService {

	@Autowired
	private ezs_customized_recordMapper customizedRecordMapper;
	
	@Override
	public int insert(ezs_customized_record record) {
		// TODO Auto-generated method stub
		return this.customizedRecordMapper.insert(record);
	}

}
