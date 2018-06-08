package com.sanbang.goods.service.imp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_price_trend;
import com.sanbang.bean.ezs_storecart;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_areaMapper;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.dao.ezs_price_trendMapper;
import com.sanbang.dao.ezs_stockMapper;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_storecartMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.CommUtil;
import com.sanbang.vo.CurrencyClass;
import com.sanbang.vo.DictionaryCode;
import com.sanbang.vo.GoodsCarInfo;
import com.sanbang.vo.QueryCondition;

/**
 * 货品相关处理
 * @author hanlongfei
 * 2018年05月12日
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
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
	private ezs_storeMapper storeMapper; 
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
	
	
	
	
	/**
	 * 查询单个货品详情
	 * @param id
	 * @return
	 */
	
	public ezs_goods getGoodsDetail(Long id){
		ezs_goods goods = new ezs_goods();
		goods = ezs_goodsMapper.getGoodsDetail(id);
		return goods;
	}
	
	public List<ezs_dvaluate> listForEvaluate(Long id){
		List<ezs_dvaluate> list = new ArrayList<ezs_dvaluate>();
		list  = ezs_dvaluateMapper.listForEvaluate(id);
		return list;
	}
	/**
	 * 添加购物车（待修改）
	 * @param goodsCart
	 * @return
	 */
	@Transactional
	public int insertCart(ezs_goodscart goodscart){
		ezs_storecart storecart = new ezs_storecart();
		int n = 0;
		try{
			//this.storecartMapper.insert(storecart);
			//this.ezs_goodscartMapper.insert();
			n = 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return n;
	}
	
	public int updateCollect(Long id,Boolean status){
		Map<String,Object> mmp = new HashMap<String,Object>();
		mmp.put("good_id", id);
		mmp.put("house", status);
		int n = ezs_documentshareMapper.updateCollect(mmp);
		return n;
	}
	
	public int insertCollect(Long id,Long userId){
		ezs_documentshare share = new ezs_documentshare();
		share.setGood_id(id);
		share.setDeleteStatus(false);
		share.setUser_id(userId);
		int n = ezs_documentshareMapper.insertSelective(share);
		return n;
	}
	
	public ezs_documentshare getCollect(Long id){
		ezs_documentshare n = ezs_documentshareMapper.selectByPrimaryKey(id);
		return n;
	}
	/**
	 * 添加订单
	 */
	public int insertOrder(ezs_orderform order){
		//判断是否加入购物车
		int n = ezs_orderformMapper.insertSelective(order);
		return n;
	}
	
	public List<ezs_goods> listForGoods(Long goodClass_id){
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		list = ezs_goodsMapper.listForGoods(goodClass_id);
		return list;
	}
	
	/**
	 * 多条件查询
	 */
	public List<ezs_goods> queryGoodsList(Long area,String[] typeIds,String[] colorIds,String[] formIds,String source,String purpose,
			String[] densitys,String[] cantilevers,String[] freelys,String[] lipolysises,String[] ashs,String[] waters,String[] tensiles,
			String[] cracks,String[] bendings,String[] flexurals,String isProtection,String goodsName){
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
		if(null != densitys){
			density1 = densitys[0];
			density2 = densitys[1];
		}
		String cantilever1 = null;
		String cantilever2 = null;
		if(null != cantilevers){
			cantilever1 = cantilevers[0];
			cantilever2 = cantilevers[1];
		}
		String freely1 = null;
		String freely2 = null;
		if(null != freelys){
			freely1 = freelys[0];
			freely2 = freelys[1];
		}
		String lipolysis1 = null;
		String lipolysis2 = null;
		if(null != lipolysises){
			lipolysis1 = lipolysises[0];
			lipolysis2 = lipolysises[1];
		}
		String ash1 = null;
		String ash2 = null;
		if(null != ashs){
			ash1 = ashs[0];
			ash2 = ashs[1];
		}
		String water1 = null;
		String water2 = null;
		if(null != waters){
			water1 = waters[0];
			water2 = waters[1];
		}
		String tensile1 = null;
		String tensile2 = null;
		if(null != tensiles){
			tensile1 = tensiles[0];
			tensile2 = tensiles[1];
		}
		String crack1 = null;
		String crack2 = null;
		if(null != cracks){
			crack1 = cracks[0];
			crack2 = cracks[1];
		}
		String bending1 = null;
		String bending2 = null;
		if(null != bendings){
			bending1 = bendings[0];
			bending2 = bendings[1];
			
		}
		String flexural1 = null;
		String flexural2 = null;
		if(null != flexurals){
			flexural1 = flexurals[0];
			flexural2 = flexurals[1];
		}
		map.put("area_id", area);
		map.put("typeList", typeList);
		map.put("colorList", colorList);
		map.put("formList", formList);
		map.put("source", source);
		map.put("purpose", purpose);
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
		map.put("protection", isProtection);
		map.put("name", goodsName);
		list = ezs_goodsMapper.queryGoodsList(map);
		return list;
	}
	/**
	 * 根据地区名称返回id
	 */
	public List<Long> areaToId(String areaName){
		return areaMapper.areaToId(areaName);
	}
	
	//查询颜色
	public List<CurrencyClass> colorList(){
		List<CurrencyClass> map = new ArrayList<CurrencyClass>();
		map = ezs_dictMapper.colorList();
		return map;
		
	}
	//查询形态
	public List<CurrencyClass> formList(){
		List<CurrencyClass> map = new ArrayList<CurrencyClass>();
		map = ezs_dictMapper.formList();
		return map;
		
	}
	
	public List<ezs_customized> customizedList(Long user_id){
		List<ezs_customized> list = new ArrayList<ezs_customized>();
		list = ezs_customizedMapper.customizedList(user_id);
		return list;
	}
	
	public int insertCustomized(ezs_customized customized){
		int n = ezs_customizedMapper.insertSelective(customized);
		return n;
	}
	
	public int insertCustomizedRecord(ezs_customized_record customizedRecord){
		int n = ezs_customized_recordMapper.insertSelective(customizedRecord);
		return n;
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
		Map<String, Object> mmp = new HashMap<>();
		ezs_storecart storeCart = null;
		//初始化查询条件
		QueryCondition queryCondition = new QueryCondition();
		queryCondition.setGoodId(goodsCart.getGoods_id());
		queryCondition.setUserId(user.getId());
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
					return mmp;
				}
			}
			if(goodsCartList!=null&&goodsCartList.size()>0){
				//若存在
				ezs_goodscart goodsCartTemp = goodsCartList.get(0);
				goodsCartTemp.setCount(goodsCartTemp.getCount()+goodsCart.getCount());
				this.ezs_goodscartMapper.updateByPrimaryKey(goodsCartTemp);
				//更新店铺购物车
				QueryCondition queryConditionTemp = new QueryCondition();
				queryConditionTemp.setStoreId(user.getStore_id());
				queryConditionTemp.setUserId(user.getId());
				queryConditionTemp.setStoreCarStatus(0);
				List<ezs_storecart> eslist = this.storecartMapper.getByCondition(queryConditionTemp);
				storeCart = eslist.get(0);
				storeCart.setTotal_price(BigDecimal.valueOf(goodsCartTemp.getCount()*good.getPrice().doubleValue()));
				this.storecartMapper.updateByPrimaryKey(storeCart);
				//good.setInventory(good.getInventory()-goodsCartTemp.getCount()-goodsCart.getCount());
				//this.ezs_goodsMapper.updateByPrimaryKey(good);
			}else{
				//商铺Id
				queryCondition.setUserId(user.getId());
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
				}
				goodsCart.setSc_id(storeCart.getId());
				goodsCart.setAddTime(new Date());
				goodsCart.setDeleteStatus(false);
				goodsCart.setPrice(good.getPrice());
				if(storeCart.getTotal_price()==null)
					totalMoney = goodsCart.getCount()*good.getPrice().doubleValue(); 
				else
					totalMoney = goodsCart.getCount()*good.getPrice().doubleValue()+storeCart.getTotal_price().doubleValue();
				this.ezs_goodscartMapper.insert(goodsCart);
				storeCart.setTotal_price(BigDecimal.valueOf(totalMoney));
				this.storecartMapper.updateByPrimaryKey(storeCart);
				//good.setInventory(good.getInventory()-goodsCart.getCount());
				//this.ezs_goodsMapper.updateByPrimaryKey(good);
			}
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "购物车添加成功");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			//事务控制须抛出异常
			throw e;
		}
		return mmp;
	}
	/**
	 * 生成订单
	 * @author zhaibin
	 */
	@Override
	@Transactional(rollbackFor=java.lang.Exception.class)
	public synchronized Map<String, Object> addOrderFormFunc(ezs_orderform orderForm, ezs_user user) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		double totalMoney = 0.0;
		//生成订单号码
		String orderFormNo = null;
		//createOrderNo();
		try {
			//orderForm.setOrder_no(orderFormNo);
			orderForm.setAddTime(new Date());
			orderForm.setDeleteStatus(false);
			orderForm.setUser_id(user.getId());
			//合同状态 1.纸质 2.电子
			orderForm.setPact_status(2);
			//订单状态 : 新增订单
			orderForm.setOrder_status(1);
			
			this.ezs_orderformMapper.insert(orderForm);
			//已购商品按store分类
			//获取购物车信息
			QueryCondition queryCondition = new QueryCondition();
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
					//goodscart.setDeleteStatus(true);
					goodscart.setOf_id(orderForm.getId());
					this.ezs_goodscartMapper.updateByPrimaryKey(goodscart);
					//更新库存
					ezs_goods tGood = this.ezs_goodsMapper.selectByPrimaryKey(goodscart.getGoods_id());
					tGood.setInventory(tGood.getInventory()-goodscart.getCount());
					this.ezs_goodsMapper.updateByPrimaryKey(tGood);
					//构建实时成交价
					savePriceTrend(goodscart,tGood,user);
				}
				//订单类型：10.自营商品订单 20.撮合商品订单
				orderForm.setOrder_type(CommUtil.order_sample_good);
				orderForm.setTotal_price(BigDecimal.valueOf(totalMoney));
				orderForm.setOrder_no(orderFormNo);
				this.ezs_orderformMapper.updateByPrimaryKey(orderForm);
				
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "订单添加成功");
			}else{
				this.ezs_orderformMapper.deleteByPrimaryKey(orderForm.getId());
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "购物车无数据");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			//事务控制须抛出异常
			throw e;
		}
		return mmp;
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
	public synchronized String createOrderNo(ezs_goods goods) {
		// TODO Auto-generated method stub
		int folwnum = this.ezs_orderformMapper.selectOrderNumByDate();
		Long rootGoodsClass = getRootOfTheGoodClass(goods.getGoodClass_id());
		StringBuffer orderNo = new StringBuffer("EM");
		orderNo.append(CommUtil.getNumber(rootGoodsClass, "00"));
		orderNo.append(CommUtil.dateToString(new Date(), "YYMMdd"));
		orderNo.append(CommUtil.getNumber(folwnum+1, "00000"));
		return orderNo.toString();
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
			throw e;
		}
        
	}
}
