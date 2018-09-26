package com.sanbang.goods.service;

import java.util.Map;

import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_user;

public interface ChildCompanyGoodsService {
	
	/**
	 * 立即购买
	 * @param user
	 * @param orderType
	 * @param goodId
	 * @param count
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> immediateAddOrderFormFunc(ezs_orderform orderForm,ezs_user user,String orderType,Long WeAddressId,ezs_goods good,Double count);

	/**
	 * 生成订单
	 * @param orderForm 订单对象
	 * @param orderType 订单类型 ： GOODS 商品订单；SAMPLE 样品订单
	 */
	public Map<String, Object> addOrderFormFunc(ezs_orderform orderForm,ezs_user user,String orderType,Long goodsCartId,ezs_goods good);
	/**
	 * 判断是否为子公司订单
	 * @param good
	 * @return
	 */
	public boolean isChildCompanyGood(ezs_goods good);

}
