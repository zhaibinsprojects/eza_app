package com.sanbang.goods.service;

import java.util.List;
import java.util.Map;

import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;

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
	public ezs_goods getGoodsDetail(Long id);
	
	/**
	 * 根据货品id查询评论列表
	 * @param id
	 */
	public List<ezs_dvaluate> listForEvaluate(Long id);
	/**
	 * 加入采购单（添加购物车）
	 * @param goodsCart 购物车实体
	 */
	public int insertCart(ezs_goodscart goodsCart);	
	
	/**
	 * 更新收藏状态
	 * @param id
	 * @return
	 */
	public int updateCollect(Long id);
	
	/**
	 * 如果是第一次收藏，就插入数据
	 * @param share
	 * @return
	 */
	public int insertCollect(Long id);
	
	/**
	 * 加入订单
	 * @param order	订单实体类
	 * @return
	 */
	public int insertOrder(ezs_orderform order);
	
	/**
	 * 同类货品
	 * @param id
	 * @return
	 */
	public List<ezs_goods> listForGoods(Long id);
	
	
	/**
	 * 自营，地区、品类筛选
	 * @param area
	 * @param type
	 */
	public List listByAreaAndType(String area,String type);
	/**
	 * 
	 * @param color	颜色
	 * @param form 形态
	 * @param purpose 用途
	 * @param source 来源
	 * @param burning 燃烧级别
	 * @param isProtection 是否环保
	 * @return
	 */
	public List listByOthers(Map map);
	
}
