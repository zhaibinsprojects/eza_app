package com.sanbang.buyer.service;

import java.util.List;
import java.util.Map;


import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_user;

public interface OrderEvaluateService {
	

	Map<String, Object> orderEvaluate(ezs_dvaluate dvaluate, ezs_accessory accessory, ezs_user user);
	
	/**
	 * 评价列表
	 * @param totalpage
	 * @param pageNo
	 * @return
	 */
	 List<ezs_dvaluate> getEvaluateList(int pageNo,long goodsid);

	

}
