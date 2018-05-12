package com.sanbang.index.service;

import java.util.List;

import com.sanbang.bean.ezs_customer;

public interface CustomerService {
	
	public void deleteById(Long id);
	
	public Object queryById(Long id);
	
	public List<ezs_customer> queryHotArea();
	
}
