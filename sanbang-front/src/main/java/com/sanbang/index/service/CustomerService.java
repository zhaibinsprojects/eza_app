package com.sanbang.index.service;

import java.util.Map;

import com.sanbang.bean.ezs_user;

public interface CustomerService {
	
	public void deleteById(Long id);
	
	public Object queryById(Long id);
	
	public Map<String, Object> getUserMessByUser(ezs_user user);
	
}
