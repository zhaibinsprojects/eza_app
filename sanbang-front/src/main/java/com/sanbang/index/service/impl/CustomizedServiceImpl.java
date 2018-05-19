package com.sanbang.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_customized;
import com.sanbang.dao.ezs_customizedMapper;
import com.sanbang.index.service.CustomizedService;

@Service
public class CustomizedServiceImpl implements CustomizedService {
	@Autowired
	private ezs_customizedMapper customizedMapper;
	
	@Override
	public int insert(ezs_customized record) {
		return this.customizedMapper.insert(record);
	}

}
