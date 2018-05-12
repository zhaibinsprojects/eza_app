package com.sanbang.index.service;

import java.util.List;

import com.sanbang.bean.ezs_goods;

public interface GoodsService {
	
	public ezs_goods queryById(Long id);
	
	public List<ezs_goods> queryByName(String name);
	
	public void addGoods(ezs_goods goods);
	
	public void modifyGoodsById(ezs_goods goods);
	
	public void dropGoodsById(ezs_goods goods);
}
