package com.sanbang.goods.service;

import java.util.List;
import java.util.Map;

import org.omg.CORBA.OBJ_ADAPTER;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_user;

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
	public int insertCart(ezs_goodscart goodsCartList);	
	
	/**
	 * 更新收藏状态
	 * @param id
	 * @return
	 */
	public int updateCollect(Long id,Boolean status);
	
	/**
	 * 如果是第一次收藏，就插入数据
	 * @param share
	 * @return
	 */
	public int insertCollect(Long id,Long userId);
	
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
	public List<ezs_goods> listForGoods(Long goodClass_id);
	
	
	/**
	 * 自营，地区、品类筛选
	 * @param area
	 * @param type
	 */
	public List<ezs_goods> listByAreaAndType(Long area,Long type);
	/**
	 * 多条件查询
	 * @param color	颜色
	 * @param form 形态
	 * @param purpose 用途
	 * @param source 来源
	 * @param burning 燃烧级别
	 * @param isProtection 是否环保
	 * @return
	 */
	public List<ezs_goods> listByOthers(Map<String,Object> map);
	
	/**
	 * 然后多条件查询所需的查询条件
	 * @return
	 */
	public List<ezs_dict> conditionList();
	
	public ezs_documentshare getCollect(Long id);
	
	/**
	 * 采购单列表
	 * @param user_id	用户id
	 * @return
	 */
	public List<ezs_customized> customizedList(Long user_id);
	
	/**
	 * 添加预约定制
	 * @param customized	定制实体
	 * @return
	 */
	public int insertCustomized(ezs_customized customized);
	
	/**
	 * 预约定制记录表
	 * @param customizedRecord	采购记录
	 * @return
	 */
	public int insertCustomizedRecord(ezs_customized_record customizedRecord);
	
	/**
	 * 添加商品到购物车
	 * @param goodscartList
	 * @param user
	 * @return
	 */
	public Map<String, Object> addGoodsCart(List<ezs_goodscart> goodscartList,ezs_user user,String sessionId);
	/**
	 * 直接下订单
	 * @param orderForm
	 * @param user
	 * @return
	 */
	public Map<String, Object> addOrderForm(ezs_orderform orderForm,ezs_user user,String sessionId);
	/**
	 * 由购物车下订单
	 * @param user
	 * @return
	 */
	public Map<String, Object> addOrderFromGoodCar(ezs_user user);
	
}
