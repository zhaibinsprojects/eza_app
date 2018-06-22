package com.sanbang.index.service;

import java.util.List;
import java.util.Map;

import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;

public interface GoodsClassService {
	
	public List<ezs_goods_class> queryGoodsClass();
	
	public ezs_goods_class queryByClassId(Long id);
	
	public ezs_goods_class queryByGoods(ezs_goods goods);
	
	public Map<String, Object> queryAllGoodsClass();
	
	public Map<String, Object> queryThirdGoodsClass(String level);
	
	public Map<String, Object> queryChildNodes();
	
	public Map<String, Object> queryGoodColorAndForm();

}
