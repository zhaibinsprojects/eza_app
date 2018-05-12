package com.sanbang.index.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_customer;
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

	@Override
	public List<ezs_customer> queryHotArea() {
		// TODO Auto-generated method stub
		List<ezs_customer> clist = this.customerMapper.selectByAreaHot();
		return clist;
	}

}
