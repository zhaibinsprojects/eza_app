package com.sanbang.index.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.dao.ezs_customerMapper;
import com.sanbang.index.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private ezs_customerMapper customerMapper;
	
	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object queryById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
