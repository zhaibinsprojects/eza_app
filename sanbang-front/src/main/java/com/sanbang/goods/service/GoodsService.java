package com.sanbang.goods.service;

import com.sanbang.bean.ezs_goods;

/**
 * 货品相关业务处理
 * @author hanlongfei
 * 2018年05月12日
 */
public interface GoodsService {
	//查询单个货品详情
	public ezs_goods getGoodsDetail(long id);
	
}
