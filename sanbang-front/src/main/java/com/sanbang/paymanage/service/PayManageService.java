package com.sanbang.paymanage.service;

import java.util.Map;

import com.sanbang.utils.Result;

public interface PayManageService {

	Result selectPayInfoByParam(Map<String, Object> map);
	

}
