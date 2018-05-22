package com.sanbang.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.dao.ezs_accessoryMapper;
import com.sanbang.index.service.AccessoryService;

@Service
public class AccessoryServiceImpl implements AccessoryService {
	@Autowired
	private ezs_accessoryMapper accessoryMapper; 

	@Override
	public ezs_accessory selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return this.accessoryMapper.selectByPrimaryKey(id);
	}

}
