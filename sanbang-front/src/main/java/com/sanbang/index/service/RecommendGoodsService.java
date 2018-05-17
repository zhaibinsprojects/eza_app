package com.sanbang.index.service;

import java.util.Map;

import com.sanbang.bean.ezs_goods;

public interface RecommendGoodsService {
	
	public ezs_goods queryById(Long id);
	
	public Map<String, Object> queryByName(String name);
	
	public void addGoods(ezs_goods goods);
	
	public void modifyGoodsById(ezs_goods goods);
	
	public void dropGoodsById(ezs_goods goods);
	
	public Map<String, Object> goodsIntroduce(String currentPage);
}
