package com.sanbang.goods.service.imp;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_price_trend;
import com.sanbang.bean.ezs_price_trend_xl;
import com.sanbang.bean.ezs_purchase_order_items;
import com.sanbang.bean.ezs_purchase_orderform;
import com.sanbang.bean.ezs_stock;
import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_storecart;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.dao.ezs_price_trendMapper;
import com.sanbang.dao.ezs_price_trend_xlMapper;
import com.sanbang.dao.ezs_purchase_order_itemsMapper;
import com.sanbang.dao.ezs_purchase_orderformMapper;
import com.sanbang.dao.ezs_stockMapper;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_storecartMapper;
import com.sanbang.dao.ezs_userMapper;
import com.sanbang.goods.service.ChildCompanyGoodsService;
import com.sanbang.utils.CommUtil;
import com.sanbang.utils.StockHelper;
import com.sanbang.utils.exponentrepager;
import com.sanbang.vo.DictionaryCode;
import com.sun.tools.example.debug.expr.ParseException;

/**
 * 子公司订单处理
 * @author LENOVO
 *
 */
@Service("childCompanyGoodsService")
public class ChildCompanyGoodsServiceImpl implements ChildCompanyGoodsService {
	
	private static Logger log = Logger.getLogger(ChildCompanyGoodsServiceImpl.class);
	@Autowired
	private com.sanbang.dao.ezs_goodsMapper ezs_goodsMapper;
	@Autowired
	private com.sanbang.dao.ezs_goodscartMapper ezs_goodscartMapper;
	@Autowired
	private com.sanbang.dao.ezs_orderformMapper ezs_orderformMapper;
	@Autowired
	private ezs_storecartMapper storecartMapper;
	@Autowired
	private ezs_stockMapper stockMapper;
	@Autowired
	private ezs_userMapper userMapper;
	@Autowired
	private ezs_goods_classMapper goodsClassMapper;
	@Autowired
	private ezs_price_trendMapper priceTrendMapper; 
	@Autowired
	private ezs_price_trend_xlMapper priceTrendXLMapper;
	@Autowired 
	private ezs_storeMapper storeMapper;
	@Autowired
	private ezs_purchase_orderformMapper purchaseOrderFormMapper;
	@Autowired
	private ezs_purchase_order_itemsMapper purchaseOrderItemsMapper;

	/**
	 * 立即购买function
	 * @throws Exception 
	 */
	@Override
	public Map<String, Object> immediateAddOrderFormFunc(ezs_orderform orderForm,ezs_user user,String orderType,
			Long WeAddressId,ezs_goods good,Double count) {
		log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"立即购买 beginning...");
		Map<String, Object> mmp = new HashMap<>();
		ezs_storecart storeCart = new ezs_storecart();
		ezs_goodscart goodsCart = new ezs_goodscart();
		try {
			//计算总价
			double totalMoney = good.getPrice().doubleValue()*count;
			if(orderType==null){
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
				mmp.put("Msg", "订单类型不能为null");
				log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"订单类型不能为null");
				return mmp;
			}
			if(count>good.getInventory()){
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
				mmp.put("Msg", "商品数量不足");
				log.info("FunctionName:"+"addGoodsCartFunc "+",context:"+"商品数量不足...");
				return mmp;
			}
			//添加订单
			orderForm.setAddTime(new Date());
			orderForm.setDeleteStatus(false);
			orderForm.setUser_id(user.getId());
			//合同状态 1.纸质 2.电子
			orderForm.setPact_status(2);
			orderForm.setPay_mode(0);
			orderForm.setPay_mode01(0);
			orderForm.setPay_mode02(0);
			//运送状态
			orderForm.setSc_status(0);
			
			orderForm.setTotal_price(BigDecimal.valueOf(totalMoney));
			//orderForm.setWeAddress_id(WeAddressId);
			orderForm.setAddress_id(WeAddressId);
			//判断订单类型：是否为子公司商品
			//子公司商品
			orderForm.setOrder_type(CommUtil.order_child_company_good);
			//订单状态 : 新增订单
			orderForm.setOrder_status(200);
			log.info("-------------------子公司商品订单---------------------");
			this.ezs_orderformMapper.insert(orderForm);
			log.info("立即购买-订单记录生成-----------------------订单ID："+orderForm.getId());
			//构建店铺购物车
			storeCart.setStore_id(user.getStore_id());
			storeCart.setDeleteStatus(false);
			storeCart.setAddTime(new Date());
			storeCart.setUser_id(user.getId());
			storeCart.setSc_status(1);// 1 已生成订单
			storeCart.setTotal_price(BigDecimal.valueOf(totalMoney));
			//构建商品购物车
			//更新设置购物车类型
			goodsCart.setCart_type(CommUtil.order_child_company_good);
			goodsCart.setAddTime(new Date());
			goodsCart.setDeleteStatus(false);
			goodsCart.setPrice(good.getPrice());
			goodsCart.setOf_id(orderForm.getId());
			goodsCart.setGoods_id(good.getId());
			goodsCart.setCount(count);
			//进行库存校验并进行更新本地库存
			boolean buyAbleFlag = checkGoodOrder(goodsCart,good,orderType,orderForm.getOrder_no());
			if(buyAbleFlag){
				this.storecartMapper.insert(storeCart);
				log.info("storecart记录生成："+storeCart.getId());
				goodsCart.setSc_id(storeCart.getId());
				this.ezs_goodscartMapper.insert(goodsCart);
				log.info("goodsCart记录生成");
				//构建实时成交价记录
				savePriceTrend(goodsCart,good,user);
				//生成供应商订单
				savePurchaseOrder(orderForm,goodsCart,good);
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
				mmp.put("Msg", "立即购买成功");
				log.info("立即购买成功");
			}else{
				//不可删除，会导致订单号重复
				log.debug("订单：ID"+orderForm.getId()+"订单号："+orderForm.getOrder_no()+" 校验失败，删除orderform表。。。。。。。。。");
				log.error("订单：ID"+orderForm.getId()+"订单号："+orderForm.getOrder_no()+" 校验失败，删除orderform表。。。。。。。。。");
				log.info("订单：ID"+orderForm.getId()+"订单号："+orderForm.getOrder_no()+" 校验失败，删除orderform表。。。。。。。。。");
				this.ezs_orderformMapper.deleteByPrimaryKey(orderForm.getId());
				mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
				mmp.put("Msg", "库存不足");
				log.info("库存不足");
			}	
		} catch (Exception e) {
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			//mmp.put("Msg", "库存不足");
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}
		//进行库存校验
		return mmp;
	}
	
	//子公司订单，生成供应商订单
	public void savePurchaseOrder(ezs_orderform orderForm, ezs_goodscart goodsCart,ezs_goods goods) {
		//卖方可见
		ezs_purchase_orderform purchaseOrderForm = new ezs_purchase_orderform();
		ezs_purchase_order_items purchaseOrderItems = new ezs_purchase_order_items(); 
		
		try{
			purchaseOrderForm.setOrder_no(getPurchaseOrderNo(orderForm));
			purchaseOrderForm.setAddTime(new Date());
			purchaseOrderForm.setDeleteStatus(false);
			purchaseOrderForm.setIfStore(0);
			purchaseOrderForm.setOrder_type(CommUtil.order_child_company_good);
			purchaseOrderForm.setPay_mode(0);
			purchaseOrderForm.setAll_price(orderForm.getTotal_price());
			purchaseOrderForm.setTotal_price(orderForm.getTotal_price());
			purchaseOrderForm.setOrder_status(200);
			purchaseOrderForm.setAddress_id(orderForm.getAddress_id());
			purchaseOrderForm.setMsg(orderForm.getMsg());
			// 合同状态 1.纸质 2.电子
			purchaseOrderForm.setPact_status(2);
			
			purchaseOrderItems.setDeleteStatus(false);
			//取货时间
			purchaseOrderItems.setArriveDate(
					mudifyDay(goods.getAddTime(),(goods.getPickup_cycle()==null||goods.getPickup_cycle().equals(""))?0:goods.getPickup_cycle())
					);
			purchaseOrderItems.setOrder_no(purchaseOrderForm.getOrder_no());
			purchaseOrderItems.setTotal_price(orderForm.getTotal_price());
			purchaseOrderItems.setAddTime(new Date());
			if(goodsCart != null && goodsCart.getCount() != null){
				purchaseOrderForm.setGoods_amount(BigDecimal.valueOf(goodsCart.getCount()));
				if(goodsCart.getGoods_id() != null){
					purchaseOrderForm.setSellerUser_id(goods.getUser_id());
					purchaseOrderForm.setBuyUser_id(orderForm.getUser_id());
					purchaseOrderItems.setGoods_id(goodsCart.getGoods_id());
					purchaseOrderItems.setGoods_price(goodsCart.getPrice());
					purchaseOrderItems.setGoods_amount(BigDecimal.valueOf(goodsCart.getCount()));
					purchaseOrderItems.setcUnitID("T");
					purchaseOrderForm.setGoodsId(goods.getId());
				}
			}
			purchaseOrderFormMapper.insertSelective(purchaseOrderForm);
			purchaseOrderItemsMapper.insertSelective(purchaseOrderItems);
			goodsCart.setPof_id(purchaseOrderForm.getId());
			ezs_goodscartMapper.updateByPrimaryKey(goodsCart);
		}catch(Exception e){
			e.printStackTrace();
			log.error("生成供应商订单异常。。。。。。。。。。。。。。。。。。。"+e.getMessage());
		}
	}
	
    /**** 
     * @param date 日期基数
     * @param days 日期变更天数
     * @return 2017-05-13
     * @throws ParseException 
     */  
    @SuppressWarnings("unused")
	private static Date mudifyDay(Date date,int days) { 
    	//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(date); 
        rightNow.add(Calendar.DATE, days);  
        Date dt1 = rightNow.getTime();  
        //String reStr = sdf.format(dt1);  
        return dt1;  
    }

	
	//生成供应商订单号码
	private String getPurchaseOrderNo(ezs_orderform orderForm) {
		String type = "00";
		int folwnum = CommUtil.null2Int(purchaseOrderFormMapper.getFlowNum())+1;
		if(CommUtil.order_child_company_good.equals(orderForm.getOrder_type())){
			type = "07";
		}
		StringBuffer orderNo = new StringBuffer("EU");
        orderNo.append(type);
        orderNo.append(CommUtil.dateToString(new Date(), "YYMMdd"));
        orderNo.append(CommUtil.getNumber(folwnum, "00000"));
        return orderNo.toString();
	}
	
	/**
	 * 逐个添加订单function
	 */
	@Override
	public Map<String, Object> addOrderFormFunc(ezs_orderform orderForm,ezs_user user,String orderType,Long goodsCartId,ezs_goods good) {
		log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"开始添加订单...");
		Map<String, Object> mmp = new HashMap<>();
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
			orderForm.setPay_mode01(0);
			orderForm.setPay_mode02(0);
			orderForm.setSc_status(0);
			//订单状态 : 新增订单
			orderForm.setOrder_status(1);
			//没卵用，仅为生成订单号码
			this.ezs_orderformMapper.insert(orderForm);
			log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"订单记录添加开始...");
			//逻辑修改，通过购物车Id进行订单添加 start........
			ezs_goodscart goodsCar = this.ezs_goodscartMapper.selectByPrimaryKey(goodsCartId);
			//ezs_goods goodTemp = null;
			ezs_storecart storecart = null; 

			log.info("获取购物车内商品信息");
			storecart = this.storecartMapper.selectByPrimaryKey(goodsCar.getSc_id());
			orderForm.setTotal_price(storecart.getTotal_price());
			//更新店铺购物车，每单只有一种商品
			storecart.setSc_status(1);//暂设定1标志 表示已生成订单
			this.storecartMapper.updateByPrimaryKeySelective(storecart);
			//写入订单编号
			this.ezs_orderformMapper.updateByPrimaryKeySelective(orderForm);
			//同步U8库存
			try {
				log.info("下单逻辑+校验库存+更新本地库存");
				checkGoodOrder(goodsCar,good,orderType,orderForm.getOrder_no());
				log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"同步U8库存成功...");
			} catch (Exception e) {
				System.out.println("同步库存异常");
				log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"同步U8库存失败...");
				e.printStackTrace();
				throw e;
			}
			//更新设置订单外键
			goodsCar.setOf_id(orderForm.getId());
			//更新设置购物车类型
			goodsCar.setCart_type(CommUtil.order_child_company_good);
			this.ezs_goodscartMapper.updateByPrimaryKeySelective(goodsCar);
			//构建实时成交价
			log.info("生成实时交易记录");
			//需判断是否为新料
			savePriceTrend(goodsCar,good,user);
			//添加货品订单
			savePurchaseOrder(orderForm, goodsCar, good);
			log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"实时交易记录生成。。。");
			//订单类型：10.自营商品订单 20.撮合商品订单
			//判断是否为自营：true-自营，false-非自营
			orderForm.setOrder_status(200);
			orderForm.setOrder_type(CommUtil.order_child_company_good);
			this.ezs_orderformMapper.updateByPrimaryKeySelective(orderForm);
			
			log.info("FunctionName:"+"addOrderFormFunc "+",context:"+"生成订单成功。。。");
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "订单添加成功");
			//逻辑修改，通过购物车Id进行订单添加 end........
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		} catch (Exception e) {
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
	 * 判断商品是否为子公司商品
	 * @param good
	 * @return
	 */
	public boolean isChildCompanyGood(ezs_goods good){
		//判断订单类型：是否为子公司商品
		ezs_user seller = null;
		ezs_store store = null;
		boolean ischildcompanygood = false;
		try{
			seller = userMapper.selectByPrimaryKey(good.getUser_id());
			store = storeMapper.selectByPrimaryKey(seller.getStore_id());
			String ischild = store.getChild_company_status();
			if(ischild!=null&&ischild.trim().equals("0"))
				ischildcompanygood = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return ischildcompanygood;
	}
	/**
	 * 构建实时成交价
	 * @param goodsCart
	 */
	public void savePriceTrend(ezs_goodscart goodCart,ezs_goods goods,ezs_user user){
		ezs_price_trend pri= new ezs_price_trend();
		ezs_price_trend_xl prixl= new ezs_price_trend_xl();
		if(getRootOfTheGoodClass(goods.getGoodClass_id()).equals("101")){
			//新料记录
			log.info("------------新料记录--------------");
	        prixl.setAddTime(new Date());
	        prixl.setDeleteStatus(false);
	        prixl.setData_time(new Date());
	        prixl.setContent("实时交易价格");
	        prixl.setGoodClass_id(goods.getGoodClass_id());
	        prixl.setPrice(goods.getSaleprice().doubleValue());
	        prixl.setPriceAttribute(""+goods.getSaleprice().doubleValue());
	        prixl.setPurpose("新料实时交易价格");
	        prixl.setRegion_id(goods.getArea_id());
		}else{
			//再生料记录
			log.info("------------再生料记录--------------");
	        pri.setData_sources(0); // 数据来源
	        pri.setType(3);
		    pri.setPrice_type(1);
	        pri.setRegion_id(goods.getArea_id());
	        pri.setGoodClass_id(getRootOfTheGoodClass(goods.getGoodClass_id()));//一级分类
	        pri.setGoodClass1_id(getSecondGoodClass(goods.getGoodClass_id()));//二级分类ID
	        pri.setGoodClass2_id(goods.getGoodClass_id());//三级品类id
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
		}
        try {
        	ezs_user seller = this.userMapper.getUserInfoByUserId(goods.getUser_id());
        	if(getRootOfTheGoodClass(goods.getGoodClass_id()).equals("101")){
        		prixl.setSource(seller.getName());
        		this.priceTrendXLMapper.insert(prixl);
        		log.info("------------新料实时交易记录--------------");
        	}else{
        		pri.setSource(seller.getName()); // 来源公司或个人	
        		pri.setSource_tel(seller.getEzs_userinfo().getTel());  // 来源者电话
        		this.priceTrendMapper.insert(pri); 
        		log.info("------------再生料实时交易记录--------------");
        	}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("FunctionName:"+"savePriceTrend "+",context:"+e.toString());
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
		int i = 0;
		if(goodsClass!=null){
			while(!goodsClass.getLevel().equals("1")){
				i++;
				goodsClass = this.goodsClassMapper.selectByPrimaryKey(goodsClass.getParent_id());
				//防止死循环
				if(i>5)
					break;
			}
			return goodsClass.getId();
		}
		return 0L;
	}
	/**
	 * 获取商品的二级品类分类ID
	 * @author zhaibin
	 * @param goodClassId
	 * @return
	 */
	private Long getSecondGoodClass(Long goodClassId){
		ezs_goods_class goodsClass = null;
		goodsClass = this.goodsClassMapper.selectByPrimaryKey(goodClassId);
		int i = 0;
		if(goodsClass!=null){
			while(!goodsClass.getLevel().equals("2")&&goodsClass.getParent_id()!=null){
				goodsClass = this.goodsClassMapper.selectByPrimaryKey(goodsClass.getParent_id());
				i++;
				if(i>5)
					break;
			}
			return goodsClass.getId();
		}
		return 0L;
	}
	/**
	 * 购物车校验（下单校验-修改同步库存）
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
					//U8库存量
					//库存单位转化，U8库存单位为Kg,本地为T
					double iQuantity = getiQuantity(CommUtil.null2Double(object.getString("iQuantity")));
					//double iQuantity = CommUtil.null2Double(object.getString("iQuantity"));
					//实际可售量
					double xaccount = StorkNumber(good,iQuantity);
					if (xaccount > account) {
						// 加入锁库库存
						ezs_stock stock = new ezs_stock();
						stock.setAddTime(new Date());
						stock.setDeleteStatus(false);
						stock.setGoodClass(2);
						//U8库存量
						stock.setiQuantity(iQuantity);
						stock.setStatus(0);
						//实际可售量-当前购买量=实际剩余量
						stock.setmQuantity(CommUtil.sub(xaccount, account));
						stock.setBuyNum(account);
						stock.setOrderNo(orderFormNo);
						stock.setGoodid(good.getId());
						stockMapper.insert(stock);
						//更新为当前剩余量
						good.setInventory(stock.getmQuantity());
						ezs_goodsMapper.updateByPrimaryKeySelective(good);
						log.debug("自营商品锁库成功！");
						bool = true;
					}else{
						//供货不足，更新为实际可售量
						good.setInventory(xaccount);
						ezs_goodsMapper.updateByPrimaryKeySelective(good);
						bool = false;
					}
				}else{
					//参考PC，U8查不到，不做同步处理
					return false;
				}
			} else {
				//非自营商品，按实际库存量计算，不用ezs_stock记录计算剩余量，但需要添加此记录
				double xaccount = good.getInventory();
				//double xaccount = StorkNumber(good,good.getInventory());
				if (xaccount >= account) {
					// 加入锁库库存
					ezs_stock stock = new ezs_stock();
					stock.setAddTime(new Date());
					stock.setDeleteStatus(false);
					stock.setGoodClass(1);
					stock.setStatus(0);
					//全部更新为当前剩余量
					stock.setiQuantity(CommUtil.sub(good.getInventory(), account));
					stock.setmQuantity(CommUtil.sub(good.getInventory(), account));
					stock.setGoodid(good.getId());
					stock.setBuyNum(account);
					stock.setOrderNo(orderFormNo);
					stockMapper.insert(stock);
					// 减去商品库存（是否在此做差？）
					good.setInventory(stock.getmQuantity());
					ezs_goodsMapper.updateByPrimaryKeySelective(good);
					log.debug("营业商品锁库成功！");
					bool = true;
				}else{
					//更新现有库存量（慎重是否可修为0）
					//good.setInventory(xaccount>=0.0?xaccount:Double.valueOf(0));
					//修改为当前可售量
					good.setInventory(xaccount);
					ezs_goodsMapper.updateByPrimaryKeySelective(good);
					bool = false;
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return bool;
	}
	
	
	private double getiQuantity(double null2Double) {
		double iQuantity = 0.0;
		try {
			iQuantity = CommUtil.div(null2Double, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iQuantity;
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
			//cktype商品类型: 1.供应商商品，2.自营商品，3.样品商品   app暂不下样品单
			//ezs_stock.status库存状态  1.释放，0.锁库
			//from ezs_stock e where e.status = '0' and e.goodid = #{goodId} and e.goodClass = #{ckType}
			List<ezs_stock> stocks = this.stockMapper.getStockByGoods(goods.getId(), cktype);
			for (ezs_stock stock : stocks) {
				stock_num = CommUtil.add(stock.getBuyNum(), stock_num);
			}
			Double InventoryTemp = CommUtil.subtract(iQuantity, stock_num);
			return InventoryTemp;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("本地库存信息更新异常信息："+e.toString());
			throw e;
		}
	}
}
