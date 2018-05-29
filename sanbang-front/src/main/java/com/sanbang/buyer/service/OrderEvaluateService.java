package com.sanbang.buyer.service;

import java.util.Map;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_user;

public interface OrderEvaluateService {
	
	public Map<String, Object> orderEvaluate(ezs_dvaluate dvaluate,ezs_accessory accessory,ezs_user user);

}
