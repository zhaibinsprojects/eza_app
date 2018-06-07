package com.sanbang.goods.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_user;
import com.sanbang.vo.CurrencyClass;

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
	 * @param areaId	地区id
	 * @param typeIds	品类id字符数组
	 * @param colorIds	颜色id字符数组
	 * @param formIds	形态id字符数组
	 * @param source	来源
	 * @param purpose	用途
	 * @param densitys	密度
	 * @param cantilevers	悬臂梁缺口冲击
	 * @param freelys	简支梁缺口冲击
	 * @param lipolysises	熔融指数（溶脂）
	 * @param ashs	灰分
	 * @param waters	水分
	 * @param tensiles	拉伸强度
	 * @param cracks	断裂伸长率
	 * @param bending	弯曲强度
	 * @param flexural	弯曲模量
	 * @param isProtection	是否环保
	 * @param goodsName	搜索框条件：商品名称
	 * @return
	 */
	public List<ezs_goods> queryGoodsList(Long area,String[] typeIds,String[] colorIds,String[] formIds,
			String source,String purpose,String[] densitys,String[] cantilevers,String[] freelys,String[] lipolysises,
			String[] ashs,String[] waters,String[] tensiles,String[] cracks,String[] bendings,String[] flexurals,
			String isProtection,String goodsName);
	
	/**
	 * 根据地区名称返回id
	 * @param areaName
	 * @return
	 */
	public Long areaToId(String areaName);
	
	/**
	 * 查询颜色
	 * @return
	 */
	public List<CurrencyClass> colorList();
	
	/**
	 * 查询形态
	 * @return
	 */
	public List<CurrencyClass> formList();
	
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
	
	
	public Map<String, Object> addGoodsCartFunc(ezs_goodscart goodsCart,ezs_user user);
	
	public Map<String, Object> addOrderFormFunc(ezs_orderform orderForm,ezs_user user);
	
	public Map<String, Object> getGoodCarFunc(ezs_user user);
	
	//创建订单号码
	public String createOrderNo(ezs_goods goods);
	
}
