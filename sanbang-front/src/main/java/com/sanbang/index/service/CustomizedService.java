package com.sanbang.index.service;

import java.util.Map;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_user;

public interface CustomizedService {
	
	public int insert(ezs_customized record);
	
	public Map<String, Object> addCustomized(ezs_user user,ezs_customized customized,ezs_customized_record customizedRecord);

}
