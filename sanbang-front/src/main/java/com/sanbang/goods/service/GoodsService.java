package com.sanbang.goods.service;

import java.util.List;

import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;

/**
 * 货品相关业务处理
 * @author hanlongfei
 * 2018年05月12日
 */
public interface GoodsService {
	/**
	 * 查询单个货品详情
	 * @param id
	 */
	public ezs_goods getGoodsDetail(long id);
	
	/**
	 * 根据货品id查询评论列表
	 * @param id
	 */
	public List<ezs_dvaluate> listForEvaluate(long id);
	/**
	 * 加入采购单（添加购物车）
	 * @param goodsCart 购物车实体
	 */
	public int insertCart(ezs_goodscart goodsCart);	
	
	
}
