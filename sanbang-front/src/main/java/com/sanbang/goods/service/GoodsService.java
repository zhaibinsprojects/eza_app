package com.sanbang.goods.service;

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
import com.sanbang.utils.Result;
import com.sanbang.vo.CurrencyClass;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.goods.GoodsVo;

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
	 * 更新收藏状态
	 * @param id
	 */
	public int updateCollect(Long id,Long userId,Boolean status);
	
	/**
	 * 如果是第一次收藏，就插入数据
	 * @param share
	 */
	public int insertCollect(Long id,Long userId);
	
	/**
	 * 同类货品
	 * @param id
	 */
	public List<GoodsVo> listForGoods(Long goodClass_id);
	
	/**
	 * @param areaList	地区id
	 * @param typeIds	品类id字符数组
	 * @param defaultId	默认
	 * @param inventory 库存量
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
	 */
	public List<GoodsInfo> queryGoodsList(List<Long> areaList,String[] typeIds,String defaultId,String inventory,String sales,String[] colorIds,String[] formIds,
			String source,String purpose,String[] prices,String[] densitys,String[] cantilevers,String[] freelys,String[] lipolysises,
			String[] ashs,String[] waters,String[] tensiles,String[] cracks,String[] bendings,String[] flexurals,String[] burnings,
			String goodsName,int pageStart);
	/**
	 * 根据地区名称返回id
	 * @param areaName
	 */
	public List<Long> areaToId(String areaName);
	
	public List<Long> queryChildId(Long area);	//省查市,或市查县、区
	
	public List<Long> queryChildIds(List<Long> listId);	//一列市查询一列县、区
	
	/**
	 * 查询颜色
	 */
	public List<CurrencyClass> colorList();
	
	/**
	 * 查询形态
	 */
	public List<CurrencyClass> formList();
	
	public ezs_documentshare getCollect(Long id,long userid);
	
	/**
	 * 采购单列表
	 * @param user_id	用户id
	 */
	public List<ezs_customized> customizedList(Long user_id);
	
	/**
	 * 添加预约定制
	 * @param customized	定制实体
	 */
	public Map<String,Object> insertCustomized(ezs_customized customized,ezs_user user);
	
	public Map<String, Object> addGoodsCartFunc(ezs_goodscart goodsCart,ezs_user user);
	
	/**
	 * 编辑购物车
	 * @param goodsCart
	 * @param user
	 */
	public Map<String,Object> editGoodsCart(Long goodsCartId,Double count,ezs_user user);
	
	/**
	 * 删除购物车
	 * @param ids 购物车id
	 */
	public Map<String,Object> deleteGoodCar(String[] ids);
	
	/**
	 * 生成订单
	 * @author zhaibin
	 * @param orderForm 订单对象
	 * @param orderType 订单类型 ： GOODS 商品订单；SAMPLE 样品订单
	 */
	public Map<String, Object> addOrderFormFunc(ezs_orderform orderForm,ezs_user user,String orderType,Long goodsCartId,ezs_goods good) throws Exception ;
	
	public Map<String, Object> getGoodCarFunc(ezs_user user,int pageNow);
	
	public Map<String, Object> preOrderFormFunc(ezs_user user,String orderType,Long goodsCartId);
	
	public Map<String, Object> getGoodInfoFromGoodCart(Map<Object, Object> mmp);
	/**
	 * 立即购买
	 * @author zhaibin
	 * @param user
	 * @param orderType
	 * @param goodId
	 * @param count
	 * @return
	 */
	public Map<String, Object> immediateAddOrderFormFunc(ezs_orderform orderForm,ezs_user user,String orderType,Long WeAddressId,ezs_goods good,Double count)throws Exception;
	
	
	/**
	 * app订单详情
	 * @param goodsid
	 * @return
	 */
	GoodsVo  getgoodsinfo(long goodsid,long userid);
	
	/**
	 * 展示pdf
	 * @param goodsid
	 * @return
	 */
	Result  getGoodsPdf(long goodsid);
	
	//根据商品id查询单个
	public ezs_goods selectByPrimaryKey(Long id);
	
	public Map<String, Object> modifyGoodCars(String[] goodsCartIds,String[] counts,ezs_user user);
	
	public Map<String, Object> getGoodCarFunc(ezs_user user,String[] goodCarIDs);
	
	public String createOrderNo(ezs_goods goods);
}
