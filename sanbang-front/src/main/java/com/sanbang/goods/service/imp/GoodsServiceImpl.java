package com.sanbang.goods.service.imp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_price_trend;
import com.sanbang.bean.ezs_stock;
import com.sanbang.bean.ezs_storecart;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.dao.ezs_price_trendMapper;
import com.sanbang.dao.ezs_stockMapper;
import com.sanbang.dao.ezs_storecartMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.CommUtil;
import com.sanbang.utils.Result;
import com.sanbang.utils.StockHelper;
import com.sanbang.utils.httpclient.HttpRemoteRequestUtils;
import com.sanbang.utils.httpclient.HttpRequestParam;
import com.sanbang.vo.CurrencyClass;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsCarInfo;
import com.sanbang.vo.GoodsOfOrderInfo;
import com.sanbang.vo.QueryCondition;
import com.sanbang.vo.goods.GoodsVo;

/**
 * 货品相关处理
 * @author hanlongfei
 * 2018年05月12日
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
	// 日志
	private static Logger log = Logger.getLogger(GoodsServiceImpl.class);
	
	@Autowired
	private com.sanbang.dao.ezs_goodsMapper ezs_goodsMapper;
	@Autowired
	private com.sanbang.dao.ezs_dvaluateMapper ezs_dvaluateMapper;
	@Autowired
	private com.sanbang.dao.ezs_goodscartMapper ezs_goodscartMapper;
	@Autowired
	private com.sanbang.dao.ezs_documentshareMapper ezs_documentshareMapper;
	@Autowired
	private com.sanbang.dao.ezs_orderformMapper ezs_orderformMapper;
	@Autowired
	private com.sanbang.dao.ezs_customizedMapper ezs_customizedMapper;
	@Autowired
	private com.sanbang.dao.ezs_customized_recordMapper ezs_customized_recordMapper;
	@Autowired
	private com.sanbang.dao.ezs_dictMapper ezs_dictMapper;
	@Autowired
	private ezs_storecartMapper storecartMapper;
	@Autowired
	private ezs_stockMapper stockMapper;
	@Autowired
	private ezs_userMapper userMapper;
	@Autowired
	private ezs_areaMapper areaMapper;
	@Autowired
	private ezs_goods_classMapper goodsClassMapper;
	@Autowired
	private ezs_price_trendMapper priceTrendMapper; 
	
	@Value("${config.processgoods.pdfurl}")
	private String processgoodspdfurl;
	
	/**
	 * 查询单个货品详情
	 * @param id
	 * @return
	 */
	public ezs_goods getGoodsDetail(Long id){
		return ezs_goodsMapper.getGoodsDetail(id);
	}
	
	public List<ezs_dvaluate> listForEvaluate(Long id){
		return ezs_dvaluateMapper.listForEvaluate(id);
	}
	
	public int updateCollect(Long id,Long userId,Boolean status){
		Map<String,Object> mmp = new HashMap<String,Object>();
		mmp.put("good_id", id);
		mmp.put("house", status);
		mmp.put("userId", userId);
		return ezs_documentshareMapper.updateCollect(mmp);
	}
	
	public int insertCollect(Long id,Long userId){
		ezs_documentshare share = new ezs_documentshare();
		share.setGood_id(id);
		share.setDeleteStatus(false);
		share.setUser_id(userId);
		return ezs_documentshareMapper.insertSelective(share);
	}
	
	public ezs_documentshare getCollect(Long id){
		return ezs_documentshareMapper.selectByPrimaryKey(id);
	}
	
	public List<ezs_goods> listForGoods(Long goodClass_id){
		return ezs_goodsMapper.listForGoods(goodClass_id);
	}
	
	/**
	 * 多条件查询
	 */
	public List<ezs_goods> queryGoodsList(List<Long> areaList,String[] typeIds,String defaultId,String inventory,String[] colorIds,
			String[] formIds,String source,String purpose,String[] prices,String[] densitys,String[] cantilevers,String[] freelys,
			String[] lipolysises,String[] ashs,String[] waters,String[] tensiles,String[] cracks,String[] bendings,String[] flexurals,
			String[] burnings,String goodsName,int pageStart){
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Long> typeList = new ArrayList<Long>();
		List<Long> colorList = new ArrayList<Long>();
		List<Long> formList = new ArrayList<Long>();
		if(null != typeIds){
			for(int n = 0; n < typeIds.length; n++){
				typeList.add(Long.valueOf(typeIds[n]));
			}
		}
		if(null != colorIds){
			for(int n = 0; n < colorIds.length; n++){
				colorList.add(Long.valueOf(colorIds[n]));
			}
		}
		if(null != formIds){
			for(int n = 0; n < formIds.length; n++){
				formList.add(Long.valueOf(formIds[n]));
			}
		}
		String density1 = null;	//值1
		String density2 = null;	//值2
		if(null != densitys && !"".equals(densitys)){
			if(densitys.length == 1){	//当只有最小值时候，前端传过来的是：值1,
				density1 = densitys[0];
			}else{
				density1 = densitys[0];
				density2 = densitys[1];
			}
			
		}
		String cantilever1 = null;
		String cantilever2 = null;
		if(null != cantilevers && !"".equals(cantilevers)){
			if(cantilevers.length == 1){
				cantilever1 = cantilevers[0];
			}else{
				cantilever1 = cantilevers[0];
				cantilever2 = cantilevers[1];
			}
			
		}
		String freely1 = null;
		String freely2 = null;
		if(null != freelys && !"".equals(freelys)){
			if(freelys.length == 1){
				freely1 = freelys[0];
			}else{
				freely1 = freelys[0];
				freely2 = freelys[1];
			}
			
		}
		String lipolysis1 = null;
		String lipolysis2 = null;
		if(null != lipolysises && !"".equals(lipolysises)){
			if(lipolysises.length == 1){
				lipolysis1 = lipolysises[0];
			}else{
				lipolysis1 = lipolysises[0];
				lipolysis2 = lipolysises[1];
			}
			
		}
		String ash1 = null;
		String ash2 = null;
		if(null != ashs && !"".equals(ashs)){
			if(ashs.length == 1){
				ash1 = ashs[0];
			}else{
				ash1 = ashs[0];
				ash2 = ashs[1];
			}
			
		}
		String water1 = null;
		String water2 = null;
		if(null != waters && !"".equals(waters)){
			if(waters.length == 1){
				water1 = waters[0];
			}else{
				water1 = waters[0];
				water2 = waters[1];
			}
			
		}
		String tensile1 = null;
		String tensile2 = null;
		if(null != tensiles && !"".equals(tensiles)){
			if(tensiles.length == 1){
				tensile1 = tensiles[0];
			}else{
				tensile1 = tensiles[0];
				tensile2 = tensiles[1];
			}
			
		}
		String crack1 = null;
		String crack2 = null;
		if(null != cracks && !"".equals(cracks)){
			if(cracks.length == 1){
				crack1 = cracks[0];
			}else{
				crack1 = cracks[0];
				crack2 = cracks[1];
			}
		}
		String bending1 = null;
		String bending2 = null;
		if(null != bendings && !"".equals(bendings)){
			if(bendings.length == 1){
				bending1 = bendings[0];
			}else{
				bending1 = bendings[0];
				bending2 = bendings[1];
			}
		}
		String flexural1 = null;
		String flexural2 = null;
		if(null != flexurals && !"".equals(flexurals)){
			if(flexurals.length == 1){
				flexural1 = flexurals[0];
			}else{
				flexural1 = flexurals[0];
				flexural2 = flexurals[1];
			}
		}
		String burning1 = null;
		String burning2 = null;
		if(null != burnings && !"".equals(burnings)){
			if(burnings.length == 1){
				burning1 = burnings[0];
			}else{
				burning1 = burnings[0];
				burning2 = burnings[1];
			}
		}
		String price1 = null;
		String price2 = null;
		if(null != prices && !"".equals(prices)){
			if(prices.length == 1){		//当只有最小值时候，前端传过来的是：值1,
				price1 = prices[0];
			}else{
				price1 = prices[0];
				price2 = prices[1];
			}
		}
		if(null != defaultId && !"".equals(defaultId)){
			map.put("addTime", defaultId);	//默认
		}
		if(null != inventory && !"".equals(inventory)){
			map.put("inventory", inventory);	//库存量
		}
		map.put("colorList", colorList);
		map.put("formList", formList);
		if(null != source && !"".equals(source)){
			map.put("source", source);
		}
		if(null != purpose && !"".equals(purpose)){
			map.put("purpose", purpose);
		}
		map.put("areaList", areaList);
		map.put("typeList", typeList);
		map.put("price1", price1);
		map.put("price2", price2);
		//重要参数（区间查询）
		map.put("density1", density1);
		map.put("density2", density2);
		map.put("cantilever1", cantilever1);
		map.put("cantilever2", cantilever2);
		map.put("freely1", freely1);
		map.put("freely2", freely2);
		map.put("lipolysis1", lipolysis1);
		map.put("lipolysis2", lipolysis2);
		map.put("ash1", ash1);
		map.put("ash2", ash2);
		map.put("water1", water1);
		map.put("water2", water2);
		map.put("tensile1", tensile1);
		map.put("tensile2", tensile2);
		map.put("crack1", crack1);
		map.put("crack2", crack2);
		map.put("bending1", bending1);
		map.put("bending2", bending2);
		map.put("flexural1", flexural1);
		map.put("flexural2", flexural2);
		map.put("burning1", burning1);
		map.put("burning2", burning2);
		map.put("name", goodsName);
		map.put("pageStart", pageStart);	//起始页
		map.put("pageSize", 10);	//每页10条
		list = ezs_goodsMapper.queryGoodsList(map);
		return list;
	}
	/**
	 * 根据地区名称返回id
	 */
	public List<Long> areaToId(String areaName){
		return areaMapper.areaToId(areaName);
	}
	
	//查询市下的区县，或查询省下的市
	@Override
	public List<Long> queryChildId(Long area) {
		return areaMapper.queryChildId(area);
	}

	@Override
	public List<Long> queryChildIds(List<Long> listId) {
		return areaMapper.queryChildIds(listId);
	}
	
	//查询颜色
	public List<CurrencyClass> colorList(){
		return ezs_dictMapper.colorList();
		
	}
	//查询形态
	public List<CurrencyClass> formList(){
		return ezs_dictMapper.formList();
	}
	
	public List<ezs_customized> customizedList(Long user_id){
		return ezs_customizedMapper.customizedList(user_id);
	}
	
	public int insertCustomized(ezs_customized customized){
		return ezs_customizedMapper.insertSelective(customized);
	}
	
	public int insertCustomizedRecord(ezs_customized_record customizedRecord){
		return ezs_customized_recordMapper.insertSelective(customizedRecord);
	}
	/**
	 * 添加购物车（逐个商品进行添加）
	 * @param goodsCart
	 * @param user
	 * @return
	 */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public synchronized Map<String, Object> addGoodsCartFunc(ezs_goodscart goodsCart, ezs_user user) {
		// TODO Auto-generated method stub
		log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"添加购物车 beginning...");
		Map<String, Object> mmp = new HashMap<>();
		ezs_storecart storeCart = null;
		//初始化查询条件
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setGoodId(goodsCart.getGoods_id());
		queryCondition.setUserId(user.getId());
		//queryCondition.setStoreCarStatus("0");
		//商品类型 金额
		double totalMoney = 0.0;
		//查询该商品是否在表内
		ezs_goods good = this.ezs_goodsMapper.selectByPrimaryKey(goodsCart.getGoods_id());
		List<ezs_goodscart> goodsCartList = this.ezs_goodscartMapper.selectByCondition(queryCondition);
		try {
			if(goodsCartList!=null&&goodsCartList.size()>0){
				double tCount = goodsCartList.get(0).getCount()+goodsCart.getCount();
				if(tCount>good.getInventory()){
					mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
					mmp.put("Msg", "商品数量不足");
					log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"商品数量不足...");
					return mmp;
				}
			}
			if(goodsCartList!=null&&goodsCartList.size()>0){
				//若存在
				log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"添加购物车存在选购商品记录--修改记录beginning");
				ezs_goodscart goodsCartTemp = goodsCartList.get(0);
				goodsCartTemp.setCount(goodsCartTemp.getCount()+goodsCart.getCount());
				this.ezs_goodscartMapper.updateByPrimaryKey(goodsCartTemp);
				//更新店铺购物车
				QueryCondition queryConditionTemp = new QueryCondition();
				queryConditionTemp.setStoreId(user.getStore_id());
				queryConditionTemp.setUserId(user.getId());
				queryConditionTemp.setStoreCarStatus(0);
				//店铺购物车已存在，获取购物车信息
				log.info("店铺购物车已存在，获取购物车信息begin...");
				List<ezs_storecart> eslist = this.storecartMapper.getByCondition(queryConditionTemp);
				try {
					storeCart = eslist.get(0);
					log.info("店铺购物车已存在，获取购物车信息end...");
					log.info("店铺购物车已存在，更新购物车信息...");
					storeCart.setTotal_price(BigDecimal.valueOf(goodsCartTemp.getCount()*good.getPrice().doubleValue()));
					this.storecartMapper.updateByPrimaryKey(storeCart);
					//good.setInventory(good.getInventory()-goodsCartTemp.getCount()-goodsCart.getCount());
					//this.ezs_goodsMapper.updateByPrimaryKey(good);
					log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"添加购物车存在选购商品记录--修改记录end");
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					log.error("未查询到店铺购物车信息 。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
					log.error(e.toString());
					throw e;
				}
			}else{
				log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"添加购物车不存在选购商品记录--添加记录beginning");
				//商铺Id
				//每个订单对应一种商品，即每种商品购物车对应一种店铺购物车，即在本用户商品购物车无此商品时，店铺购车亦如此；故注此代码
				/*queryCondition.setUserId(user.getId());
				queryCondition.setStoreCarStatus(0);
				List<ezs_storecart> storeCarList = this.storecartMapper.getByUserId(queryCondition);
				if(storeCarList!=null&&storeCarList.size()>0){
					storeCart = storeCarList.get(0);
				}else{
					storeCart = new ezs_storecart();
					storeCart.setStore_id(user.getStore_id());
					storeCart.setDeleteStatus(false);
					storeCart.setAddTime(new Date());
					storeCart.setUser_id(user.getId());
					storeCart.setSc_status(0);
					this.storecartMapper.insert(storeCart);	
				}*/
				
				storeCart = new ezs_storecart();
				storeCart.setStore_id(user.getStore_id());
				storeCart.setDeleteStatus(false);
				storeCart.setAddTime(new Date());
				storeCart.setUser_id(user.getId());
				storeCart.setSc_status(0);
				this.storecartMapper.insert(storeCart);
				
				goodsCart.setSc_id(storeCart.getId());
				goodsCart.setAddTime(new Date());
				goodsCart.setDeleteStatus(false);
				goodsCart.setPrice(good.getPrice());
				//判断购物车类型 0-自营商品 ， 1-非自营商品
				//不在此处设置购物车类型，生成订单时设置
				/*if("0".equals(good.getGood_self())){
					goodsCart.setCart_type(CommUtil.order_self_good);
				}else{
					goodsCart.setCart_type(CommUtil.match_goods);
				}*/
				if(storeCart.getTotal_price()==null)
					totalMoney = goodsCart.getCount()*good.getPrice().doubleValue(); 
				else
					totalMoney = goodsCart.getCount()*good.getPrice().doubleValue()+storeCart.getTotal_price().doubleValue();
				this.ezs_goodscartMapper.insert(goodsCart);
				storeCart.setTotal_price(BigDecimal.valueOf(totalMoney));
				this.storecartMapper.updateByPrimaryKey(storeCart);
				//good.setInventory(good.getInventory()-goodsCart.getCount());
				//this.ezs_goodsMapper.updateByPrimaryKey(good);
				log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"添加购物车不存在选购商品记录--添加记录end");
			}
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "购物车添加成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("FunctionName:"+"addGoodsCartFunc "+",context:"+"添加购物车异常");
			log.error(e.toString());
			log.error(e.getMessage());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			//事务控制须抛出异常
			throw e;
		}
		return mmp;
	}
	/**
	 * 编辑购物车（编辑单个商品）
	 * @param goodsId	商品id
	 * @param count	  编辑的最终数量
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor=java.lang.Exception.class)
	public synchronized Map<String,Object> editGoodsCart(Long goodsId,Double count,ezs_user user){
		//1、先确认这个商品是否在购物车中存在，没有则提示添加购物车
		//2、然后就是查询改商品的库存量，如果提示超过库存量，则提示超过现有量
		//3、更新两张表的数据（ezs_storecart、ezs_goodscart）
		//4、然后就是查询单价并计算总价，返回到前端
		log.info("编辑购物车begining...");
		Map<String, Object> map = new HashMap<String,Object>();
		try{
			//1先判断商品存在否以及库存量
			ezs_goods goods = this.ezs_goodsMapper.selectByPrimaryKey(goodsId);
			if(null != goods){
				if(count > goods.getInventory()){
					map.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
					map.put("Msg", "商品数量不足");
					map.put("count", goods.getInventory());
					log.info("编辑购物车方法：商品数量不足...");
					return map;
				}
				Map<String, Object> mp = new HashMap<String,Object>();
				mp.put("goodsId",goodsId);
				mp.put("userId",user.getId());
				ezs_goodscart goodsCart = ezs_goodscartMapper.selectByGoodsId(mp);
				//2然后判断是否存在购物车中（若存在，则说明之前已经添加进来，那么店铺购物车则也是存在的，下面只做更新操作即可）
				if(null != goodsCart){
					double totalPrice = count*(goods.getPrice().doubleValue());
					ezs_storecart storeCart = new ezs_storecart();
					storeCart.setTotal_price(BigDecimal.valueOf(totalPrice));
					storeCart.setId(goodsCart.getSc_id());
					goodsCart.setCount(count);
					int n = storecartMapper.updateByPrimaryKeySelective(storeCart);
					int m = ezs_goodscartMapper.updateByPrimaryKeySelective(goodsCart);
					if( n > 0 && m > 0){
						map.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
						map.put("Msg", "购物车数据更新成功");
						map.put("totalPrice", totalPrice);
						log.info("编辑购物车方法：数据更新成功...");
						return map;
					}
				}else{
					map.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
					map.put("Msg", "请先添加购物车");
					log.info("编辑购物车方法：购物车里不存在该商品...");
					return map;
				}
			}else{
				map.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
				map.put("Msg", "该商品不存在");
				log.info("编辑购物车方法：不存在该商品...");
				return map;
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info("编辑购物车异常");
			map.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			map.put("Msg", "参数传递有误");
			throw e;
		}
		log.info("编辑购物车end...");
		return map;
	}
	/**
	 * 生成订单
	 * @author zhaibin
	 * @param orderForm 订单对象
	 * @param orderType 订单类型 ： GOODS 商品订单；SAMPLE 样品订单
	 * @throws Exception 
	 */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public synchronized Map<String, Object> addOrderFormFunc(ezs_orderform orderForm, ezs_user user,String orderType,Long goodsCartId) {
		// TODO Auto-generated method stub
		log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"开始添加订单...");
		Map<String, Object> mmp = new HashMap<>();
		//生成订单号码
		String orderFormNo = null;
		//createOrderNo();
		if(orderType==null){
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "订单类型不能为null");
			log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"订单类型不能为null");
			return mmp;
		}
		try {
			orderForm.setAddTime(new Date());
			orderForm.setDeleteStatus(false);
			orderForm.setUser_id(user.getId());
			//合同状态 1.纸质 2.电子
			orderForm.setPact_status(2);
			orderForm.setPay_mode(0);
			orderForm.setPay_mode01(1);
			orderForm.setPay_mode02(1);
			orderForm.setSc_status(1);
			//订单状态 : 新增订单
			orderForm.setOrder_status(1);
			this.ezs_orderformMapper.insert(orderForm);
			log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"订单记录添加开始...");
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			//逻辑修改，通过购物车Id进行订单添加 start........
			ezs_goodscart goodsCar = this.ezs_goodscartMapper.selectByPrimaryKey(goodsCartId);
			ezs_goods goodTemp = null;
			ezs_storecart storecart = null; 
			if(goodsCar!=null){
				log.info("获取购物车内商品信息");
				goodTemp = this.ezs_goodsMapper.selectByPrimaryKey(goodsCar.getGoods_id());
				storecart = this.storecartMapper.selectByPrimaryKey(goodsCar.getSc_id());
				orderForm.setTotal_price(storecart.getTotal_price());
				//更新店铺购物车，每单只有一种商品
				storecart.setSc_status(1);//暂设定1标志 表示已生成订单
				this.storecartMapper.updateByPrimaryKey(storecart);
				//没卵用，仅为生成订单号码
				orderFormNo = createOrderNo(goodTemp);
				//同步U8库存
				try {
					log.info("下单逻辑+校验库存+更新本地库存");
					checkGoodOrder(goodsCar,goodTemp,orderType,orderFormNo);
					log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"同步U8库存成功...");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("同步库存异常");
					log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"同步U8库存失败...");
					e.printStackTrace();
					throw e;
				}
				//更新设置订单外键
				goodsCar.setOf_id(orderForm.getId());
				//更新设置购物车类型
				if(orderType.trim().equals("GOODS")){
					goodsCar.setCart_type((goodTemp.getGood_self().equals(true)?CommUtil.order_self_good:CommUtil.match_goods));
				}else if(orderType.trim().equals("SAMPLE")){
					//样品
					goodsCar.setCart_type(CommUtil.sample_goods);
				}
				this.ezs_goodscartMapper.updateByPrimaryKey(goodsCar);
				//构建实时成交价
				log.info("生成实时交易记录");
				savePriceTrend(goodsCar,goodTemp,user);
				log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"实时交易记录生成。。。");
				//订单类型：10.自营商品订单 20.撮合商品订单
				//判断是否为自营：true-自营，false-非自营
				if(orderType.trim().equals("GOODS")){
					orderForm.setOrder_type((goodTemp.getGood_self().equals(true)?CommUtil.order_self_good:CommUtil.order_match_good));
				}else if(orderType.trim().equals("SAMPLE")){
					//样品订单
					orderForm.setOrder_type(CommUtil.order_sample_good);
				}
				orderForm.setOrder_no(orderFormNo);
				this.ezs_orderformMapper.updateByPrimaryKey(orderForm);
				
				log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"生成订单成功。。。");
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "订单添加成功");
			}else{
				//购物车ID有误，未查询到商品购物车信息
				this.ezs_orderformMapper.deleteByPrimaryKey(orderForm.getId());
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
				mmp.put("Msg", "商品购物车查询失败");
				log.error("商品购物车查询失败");
			}
			
			//逻辑修改，通过购物车Id进行订单添加 end........
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			
			//已购商品按store分类
			//获取购物车信息
			/*QueryCondition queryCondition = new QueryCondition();
			queryCondition.setUserId(user.getId());
			queryCondition.setStoreCarStatus(0);
			List<ezs_storecart> storeCarList = this.storecartMapper.getByUserId(queryCondition);
			if(storeCarList!=null&&storeCarList.size()>0){
				//每个订单仅产生一条ezs_storecart记录（一条ezs_storecart 对应多条 ezs_goodscart）
				ezs_storecart storeCartTemp = storeCarList.get(0);
				totalMoney += storeCartTemp.getTotal_price().doubleValue();
				//更新商铺记录状态
				storeCartTemp.setSc_status(1);//暂设定1标志 表示已生成订单
				this.storecartMapper.updateByPrimaryKey(storeCartTemp);
				//更新商品购物车
				QueryCondition queryCondition01 = new QueryCondition();
				queryCondition01.setUserId(user.getId());
				queryCondition01.setStoreCarId(storeCartTemp.getId());
				List<ezs_goodscart> goodsCarList = this.ezs_goodscartMapper.selectByStoreCarId(queryCondition01);
				//没卵用，仅为生成订单号码
				ezs_goods goodTemp = this.ezs_goodsMapper.selectByPrimaryKey(goodsCarList.get(0).getGoods_id());
				orderFormNo = createOrderNo(goodTemp);
				for (ezs_goodscart goodscart : goodsCarList) {
					//更新库存
					ezs_goods tGood = this.ezs_goodsMapper.selectByPrimaryKey(goodscart.getGoods_id());
					//tGood.setInventory(tGood.getInventory()-goodscart.getCount());
					//this.ezs_goodsMapper.updateByPrimaryKey(tGood);
					
					//同步U8库存
					boolean goodCountCheckFlag = false;
					try {
						goodCountCheckFlag = checkGoods(goodscart,tGood);
						log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"同步U8库存成功...");
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("同步库存异常");
						log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"同步U8库存失败...");
						e.printStackTrace();
						throw e;
					}
					if(goodCountCheckFlag==true){
						//锁库记录并更新本地库存
						log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"锁库并更新库存开始...");
						addStockRecord(goodscart,tGood,orderFormNo);
						log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"锁库并更新库存成功...");
					}
					//更新设置订单外键
					goodscart.setOf_id(orderForm.getId());
					//更新设置购物车类型
					if(orderType.trim().equals("GOODS")){
						goodscart.setCart_type((tGood.getGood_self().equals(true)?CommUtil.order_self_good:CommUtil.match_goods));
					}else if(orderType.trim().equals("SAMPLE")){
						//样品
						goodscart.setCart_type(CommUtil.sample_goods);
					}
					this.ezs_goodscartMapper.updateByPrimaryKey(goodscart);
					//构建实时成交价
					savePriceTrend(goodscart,tGood,user);
					log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"实时交易记录生成。。。");
				}
				//订单类型：10.自营商品订单 20.撮合商品订单
				//判断是否为自营：true-自营，false-非自营
				if(orderType.trim().equals("GOODS")){
					orderForm.setOrder_type((goodTemp.getGood_self().equals(true)?CommUtil.order_self_good:CommUtil.order_match_good));
				}else if(orderType.trim().equals("SAMPLE")){
					//样品订单
					orderForm.setOrder_type(CommUtil.order_sample_good);
				}
				orderForm.setTotal_price(BigDecimal.valueOf(totalMoney));
				orderForm.setOrder_no(orderFormNo);
				this.ezs_orderformMapper.updateByPrimaryKey(orderForm);
				
				log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"生成订单成功。。。");
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "订单添加成功");
			}else{
				this.ezs_orderformMapper.deleteByPrimaryKey(orderForm.getId());
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "购物车无数据");
			}*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("FunctionName:"+"addOrderFormFunc "+",context:"+"生成订单失败。。。");
			log.error("FunctionName:"+"addOrderFormFunc "+",context:"+e.getMessage());
			log.error("FunctionName:"+"addOrderFormFunc "+",context:"+e.toString());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			//事务控制须抛出异常
			throw e;
		}
		log.info("添加订单完成");
		return mmp;
	}
	
	
	
	/**
	 * 预提交订单校验，不做落库操作
	 * @author zhaibin
	 */
	@Override
	public Map<String, Object> preOrderFormFunc(ezs_user user, String orderType,Long goodsCartId) {
		// TODO Auto-generated method stub
		log.info("FunctionName:"+"preOrderFormFunc "+",context:"+"开始预提交订单...");
		Map<String, Object> goodsCartMp = new HashMap<>();
		if(orderType==null){
			goodsCartMp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			goodsCartMp.put("Msg", "订单类型不能为null");
			log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"订单类型不能为null");
			return goodsCartMp;
		}
		try {
			log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"预提交订单校验开始...");
			ezs_goodscart goodsCar = this.ezs_goodscartMapper.selectByPrimaryKey(goodsCartId);
			ezs_goods goodTemp = null;
			if(goodsCar!=null){
				goodTemp = this.ezs_goodsMapper.selectByPrimaryKey(goodsCar.getGoods_id());
				//同步U8库存
				boolean goodCountCheckFlag = false;
				try {
					//检验商品库存信息
					goodCountCheckFlag = checkGoodCart(goodsCar,goodTemp,orderType);
					log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"预提交测试，通过U8库进行库存量校验...");
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("检验商品库存信息失败");
					log.error("FunctionName:"+"addOrderFormFunc "+",context:"+e.toString());
					e.printStackTrace();
					throw e;
				}
				if(goodCountCheckFlag==true){
					//商品校验成功，可下单
					log.info("预提交测试，通过U8库进行库存量校验通过");
					goodsCartMp.put("GoodCartID", goodsCartId);
					goodsCartMp.put("GoodCartIDFlag", true);
				}else{
					//校验失败，库存不足
					log.info("预提交测试，通过U8库进行库存量校验未通过");
					goodsCartMp.put("GoodCartID", goodsCartId);
					goodsCartMp.put("GoodCartIDFlag", false);
				}
				//构建实时成交价
				goodsCartMp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				goodsCartMp.put("Msg", "校验执行完成");
			}else{
				//购物车ID有误，未查询到商品购物车信息
				goodsCartMp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
				goodsCartMp.put("Msg", "校验执行完成");
				log.error("商品购物车查询失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("FunctionName:"+"addOrderFormFunc "+",context:"+e.getMessage());
			log.error("FunctionName:"+"addOrderFormFunc "+",context:"+e.toString());
			goodsCartMp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			goodsCartMp.put("Msg", "参数传递有误");
			//事务控制须抛出异常
			throw e;
		}
		log.info("添加订单预测试完成");
		return goodsCartMp;
	}
	/**
	 * 获取购物车商品信息
	 * @author zhaibin
	 * @param user
	 * @return
	 */
	@Override
	public Map<String, Object> getGoodCarFunc(ezs_user user) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		List<GoodsCarInfo> goodCarInfoList = null;
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setUserId(user.getId());
		try {
			goodCarInfoList = this.ezs_goodscartMapper.selectByUserId(queryCondition);
			if(goodCarInfoList!=null&&goodCarInfoList.size()>0){
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Obj", goodCarInfoList);
				mmp.put("Msg", "查询成功");
			}else{
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "购物车没有数据");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("ErrorMessage:"+e.toString());
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	/**
	 * 创建订单号码
	 * @author zhaibin
	 * @param goods
	 * @return
	 */
	public String createOrderNo(ezs_goods goods) {
		// TODO Auto-generated method stub
		try {
			log.info("FunctionName:"+"createOrderNo "+",context:"+"创建订单号。。。。。。。");
			int folwnum = this.ezs_orderformMapper.selectOrderNumByDate();
			Long rootGoodsClass = getRootOfTheGoodClass(goods.getGoodClass_id());
			StringBuffer orderNo = new StringBuffer("EM");
			orderNo.append(CommUtil.getNumber(rootGoodsClass, "00"));
			orderNo.append(CommUtil.dateToString(new Date(), "YYMMdd"));
			orderNo.append(CommUtil.getNumber(folwnum+1, "00000"));
			log.info("FunctionName:"+"createOrderNo "+",context:"+"创建订单号成功");
			return orderNo.toString();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("生成订单号失败："+e.toString());
			throw e;
		}
	}
	/**
	 * 循环获取最高级商品种类ID
	 * @author zhaibin
	 * @param goodClassId
	 * @return
	 */
	private Long getRootOfTheGoodClass(Long goodClassId){
		ezs_goods_class goodsClass = null;
		goodsClass = this.goodsClassMapper.selectByPrimaryKey(goodClassId);
		if(goodsClass!=null){
			while(!goodsClass.getLevel().equals("1")){
				goodsClass = this.goodsClassMapper.selectByPrimaryKey(goodsClass.getParent_id());
			}
			return goodsClass.getId();
		}
		return 0L;
	}
	
	/**
	 * 构建实时成交价
	 * @param goodsCart
	 */
	public void savePriceTrend(ezs_goodscart goodCart,ezs_goods goods,ezs_user user){
		ezs_price_trend pri= new ezs_price_trend();
        pri.setData_sources(0); // 数据来源
        pri.setType(3);
	    pri.setPrice_type(1);
        pri.setRegion_id(goods.getArea_id());
        pri.setGoodClass_id(getRootOfTheGoodClass(goods.getGoodClass_id()));//一级分类
        //pri.setGoodClass1(goodCart.getGoods().getGoodClass().getParent());//二级分类
        //pri.setGoodClass2(goodCart.getGoods().getGoodClass());//三级分类
        pri.setPrice(goodCart.getPrice().doubleValue());  //单价/元
        pri.setColor_id(goods.getColor_id());
        pri.setForm_id(goods.getForm_id());
        pri.setDensity(goods.getDensity()); // 密度	
        pri.setCantilever(goods.getCantilever());// 悬臂梁缺口冲击	
        pri.setFreely(goods.getFreely());// 简支梁缺口冲击 
        pri.setLipolysis(goods.getLipolysis()); // 	溶脂 
        pri.setAsh(goods.getAsh());  // 	灰分	
        pri.setWater(goods.getWater()); // 水分	 
        pri.setTensile(goods.getTensile());// 拉伸强度	
        pri.setCrack(goods.getCrack()); // 断裂伸长率	 
        pri.setBending(goods.getBending());// 弯曲强度	
        pri.setFlexural(goods.getFlexural());// 弯曲模量	 
        pri.setBurning(goods.getBurning());// 燃烧等级
        pri.setProtection(goods.getProtection());  //环保
        pri.setData_time(new Date());
        pri.setStatus(2);
        pri.setAddTime(new Date());
        pri.setUser_id(user.getId());
        pri.setDeleteStatus(false);
        try {
        	ezs_user seller = this.userMapper.getUserInfoByUserId(goods.getUser_id());
        	pri.setSource(seller.getName()); // 来源公司或个人	
        	pri.setSource_tel(seller.getEzs_userinfo().getTel());  // 来源者电话	
        	this.priceTrendMapper.insert(pri); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("FunctionName:"+"savePriceTrend "+",context:"+e.toString());
			throw e;
		}
	}
	
	/**
	 * 更新现有商品库存量
	 * @param goods	商品
	 * @param iQuantity 商品库存量
	 * @return 返回 商品剩余量（真是库存）
	 */
	public double StorkNumber(ezs_goods goods, double iQuantity) {
		// 定义锁库量
		log.info("本地库存信息更新。。。。。。。。。");
		try {
			//上次购买量
			double stock_num = 0.0;
			int cktype = 1;
			if (goods.getGood_self().equals(true)) {
				cktype = 2;
			}
			//获取该商品的购买量（不含本次的购买量）（在添加订单时添加锁表记录）
			//cktype商品类型: 1.供应商商品，2.自营商品，3.样品商品
			//ezs_stock.status库存状态  1.释放，0.锁库
			//from ezs_stock e where e.status = '0' and e.goodid = #{goodId} and e.goodClass = #{ckType}
			List<ezs_stock> stocks = this.stockMapper.getStockByGoods(goods.getId(), cktype);
			for (ezs_stock stock : stocks) {
				stock_num += CommUtil.add(stock.getBuyNum(), stock_num);
			}
			Double InventoryTemp = CommUtil.subtract(iQuantity, stock_num);
			return InventoryTemp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("本地库存信息更新异常信息："+e.toString());
			throw e;
		}
	}
	
	@Override
	public GoodsVo getgoodsinfo(long goodsid) {
		GoodsVo  goodsVo =	ezs_goodsMapper.getgoodsinfo(goodsid);
		return goodsVo;
	}

	@SuppressWarnings("unused")
	@Override
	public Result getGoodsPdf(long goodsid) {
		Result result=Result.failure();
		String url=processgoodspdfurl;
		net.sf.json.JSONObject callBackRet = null;
		HttpRequestParam httpParam = new HttpRequestParam();
		try {
			httpParam.addUrlParams(new BasicNameValuePair("goodsid",String.valueOf(goodsid)));
			
				callBackRet= HttpRemoteRequestUtils.doPost(url, httpParam);
				Map<String, Object> mv = new HashMap<>();
				mv.put("pdfurl", "http://www.ezaisheng.com/upload/pdf/quality.pdf?"+System.currentTimeMillis());
				result.setObj(mv);
				result.setSuccess(true);
				result.setMsg("请求成功");
				result.setErrorcode(DictionaryCode.ERROR_WEB_PARAM_ERROR);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("系统错误");
			result.setErrorcode(DictionaryCode.ERROR_WEB_SERVER_ERROR);
			log.info("系统错误", e);
		}
		return result;
	}
	/**
	 * 购物车校验
	 * @author zhaibin
	 * @return
	 */
	private boolean checkGoodCart(ezs_goodscart goodCar,ezs_goods good,String orderType){
		log.info("获取商品实际库存。。。。。");
		double account = goodCar.getCount();// 购买量
		boolean bool = false;
		String saveFlag = (orderType=="GOODS"?"02":"01");
		try {
			if (goodCar != null && good.getGood_self().equals(true)) {
				// 自营平台锁库
				// 获取真实库存
				// 01样品库存，02商品库存
				log.info("自营商品。。。。。。。。。");
				JSONObject object = StockHelper.getStock(good.getGood_no(), saveFlag);
				if (object != null) {
					// 现有真实库存量
					log.info("实际库存。。。。。。。。。");
					//double iQuantity = CommUtil.null2Double(object.getString("iQuantity"));
					double xaccount = StorkNumber(good,CommUtil.null2Double(object.getString("iQuantity")));
					if (xaccount > account) {
						bool = true;
					}
				}
			} else {
				// 供应商锁库
				log.info("非自营商品，不访问U8库存信息。。。。。。。。。");
				double xaccount = StorkNumber(good, good.getInventory());
				if (xaccount >= account) {
					bool = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return bool;
	}
	/**
	 * 购物车校验
	 * @author zhaibin
	 * @return
	 */
	private boolean checkGoodOrder(ezs_goodscart goodCar,ezs_goods good,String orderType,String orderFormNo){
		log.info("获取商品实际库存。。。。。");
		double account = goodCar.getCount();// 购买量
		boolean bool = false;
		String saveFlag = (orderType=="GOODS"?"02":"01");
		try {
			if (goodCar != null && good.getGood_self().equals(true)) {
				// 自营平台锁库
				// 获取真实库存
				// 01样品库存，02商品库存
				log.info("自营商品。。。。。。。。。");
				JSONObject object = StockHelper.getStock(good.getGood_no(), saveFlag);
				if (object != null) {
					// 现有真实库存量
					log.info("实际库存。。。。。。。。。");
					double iQuantity = CommUtil.null2Double(object.getString("iQuantity"));
					double xaccount = StorkNumber(good,CommUtil.null2Double(object.getString("iQuantity")));
					if (xaccount > account) {
						// 加入锁库库存
						ezs_stock stock = new ezs_stock();
						stock.setAddTime(new Date());
						stock.setDeleteStatus(false);
						stock.setGoodClass(2);
						stock.setiQuantity(iQuantity);
						stock.setStatus(0);
						stock.setmQuantity(CommUtil.sub(xaccount, account));
						stock.setBuyNum(account);
						stock.setOrderNo(orderFormNo);
						stock.setGoodid(good.getId());
						stockMapper.insert(stock);
						// 减去商品库存
						good.setInventory(stock.getmQuantity());
						ezs_goodsMapper.updateByPrimaryKey(good);
						log.debug("自营商品锁库成功！");
					}else{
						//供货不足，更新现有库存量
						good.setInventory(xaccount);
						ezs_goodsMapper.updateByPrimaryKey(good);
					}
				}
			} else {
				// 供应商锁库
				double xaccount = good.getInventory();
				if (xaccount >= account) {
					// 加入锁库库存
					ezs_stock stock = new ezs_stock();
					stock.setAddTime(new Date());
					stock.setDeleteStatus(false);
					stock.setGoodClass(1);
					stock.setStatus(0);
					stock.setiQuantity(CommUtil.sub(good.getInventory(), account));
					stock.setmQuantity(CommUtil.sub(good.getInventory(), account));
					stock.setGoodid(good.getId());
					stock.setBuyNum(account);
					stock.setOrderNo(orderFormNo);
					stockMapper.insert(stock);
					// 减去商品库存
					good.setInventory(stock.getmQuantity());
					ezs_goodsMapper.updateByPrimaryKey(good);
					log.debug("营业商品锁库成功！");
				}else{
					//更新现有库存量
					good.setInventory(xaccount);
					ezs_goodsMapper.updateByPrimaryKey(good);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		return bool;
	}

	@Override
	public Map<String, Object> getGoodInfoFromGoodCart(Map<Object, Object> mmp) {
		// TODO Auto-generated method stub
		Map<String, Object> tempMap = new HashMap<>();
		List<GoodsOfOrderInfo> goodsInfoList = new ArrayList<>();
		Set<Object> goodCartNoSet = mmp.keySet();
		for (Object obj : goodCartNoSet) {
			GoodsOfOrderInfo goodsInfo = new GoodsOfOrderInfo();
			System.out.println(Long.valueOf(obj.toString()));
			ezs_goodscart goodsCart = this.ezs_goodscartMapper.selectByPrimaryKey(Long.valueOf(obj.toString()));
			ezs_goods goodTemp = this.ezs_goodsMapper.selectByPrimaryKey(goodsCart.getGoods_id());
			goodsInfo.setGoodsCartID(Long.valueOf(obj.toString()));
			goodsInfo.setGoodsID(goodsCart.getGoods_id());
			goodsInfo.setGoodsName(goodTemp.getName());
			goodsInfo.setStatus((boolean)mmp.get(obj.toString()));
			if((boolean)mmp.get(obj.toString())==false){
				goodsInfo.setMessage("商品"+goodTemp.getName()+"库存不足！！");
			}
			goodsInfoList.add(goodsInfo);
		}
		tempMap.put("Obj", goodsInfoList);
		return tempMap;
	}
}
