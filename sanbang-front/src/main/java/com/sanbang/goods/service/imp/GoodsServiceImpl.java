package com.sanbang.goods.service.imp;

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
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.bean.ezs_storecart;
import com.sanbang.bean.ezs_user;
import com.sanbang.dao.ezs_storeMapper;
import com.sanbang.dao.ezs_storecartMapper;
import com.sanbang.goods.service.GoodsService;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

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
	
	public List<ezs_goods> listByAreaAndType(Long area,Long type){
		Map<String,Long> mmp = new HashMap<String,Long>();
		mmp.put("area_id",area);
		mmp.put("goodClass_id",type);
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		list = ezs_goodsMapper.listByAreaAndType(mmp);
		return list;
	}
	
	public List<ezs_goods> listByOthers(Map<String,Object> map){
		List<ezs_goods> list = new ArrayList<ezs_goods>();
		list = ezs_goodsMapper.listByOthers(map);
		return list;
	}
	
	public List<ezs_dict> conditionList(){
		List<ezs_dict> list = new ArrayList<ezs_dict>();
		list = ezs_dictMapper.conditionList();
		return list;
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
	 * add by zhaibin
	 * 添加购物车
	 */
	@Override
	public Map<String, Object> addGoodsCart(List<ezs_goodscart> goodscartList, ezs_user user) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		ezs_storecart storeCart = new ezs_storecart();
		if(goodscartList.size()<=0){
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			return mmp;
		}
		//同步数据
		for (ezs_goodscart goodscart : goodscartList) {
			double goodAccount = goodscart.getAcount();
			
			
		}
		
		
		try {
			storeCart.setStore_id(user.getStore_id());
			storeCart.setDeleteStatus(false);
			storeCart.setAddTime(new Date());
			storeCart.setUser_id(user.getId());
			storeCart.setSc_status(0);
			//storeCart.setCart_session_id();
			this.storecartMapper.insert(storeCart);
			for (ezs_goodscart goodscart : goodscartList) {
				goodscart.setSc_id(user.getStore_id());
				goodscart.setAddTime(new Date());
				goodscart.setDeleteStatus(false);
				goodscart.setSc_id(storeCart.getId());
				this.ezs_goodscartMapper.insert(goodscart);
			}
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
		}
		return mmp;
	}
	/**
	 * add by zhaibin
	 * 直接下订单
	 */
	@Override
	public Map<String, Object> addOrderForm(List<ezs_goodscart> goodscartList,ezs_user user) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		ezs_storecart storeCart = new ezs_storecart();
		ezs_orderform orderForm = new ezs_orderform(); 
		if(goodscartList.size()<=0){
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "参数传递有误");
			return mmp;
		}
		try {
			storeCart.setStore_id(user.getStore_id());
			storeCart.setDeleteStatus(false);
			storeCart.setAddTime(new Date());
			storeCart.setUser_id(user.getId());
			storeCart.setSc_status(0);
			//storeCart.setCart_session_id();
			this.storecartMapper.insert(storeCart);
			
			orderForm.setAddTime(new Date());
			orderForm.setDeleteStatus(false);
			orderForm.setOrder_no("");
			orderForm.setUser_id(user.getId());
			this.ezs_orderformMapper.insert(orderForm);
			
			for (ezs_goodscart goodscart : goodscartList) {
				goodscart.setSc_id(user.getStore_id());
				goodscart.setAddTime(new Date());
				goodscart.setDeleteStatus(false);
				//外键01
				goodscart.setSc_id(storeCart.getId());
				//外键02
				goodscart.setOf_id(orderForm.getId());
				this.ezs_goodscartMapper.insert(goodscart);
			}
			
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "");
		}
		return mmp;
	}
	/**
	 * 由购物车下订单
	 */
	@Override
	public Map<String, Object> addOrderFromGoodCar(ezs_user user) {
		// TODO Auto-generated method stub
		Map<String, Object> mmp = new HashMap<>();
		ezs_goodscart goodscart = null;
		ezs_orderform orderform = new ezs_orderform();
		
		try {
			goodscart = this.ezs_goodscartMapper.selectByPrimaryKey(Long.getLong(""));

			orderform.setAddTime(new Date());
			orderform.setDeleteStatus(false);
			orderform.setOrder_no("");
			orderform.setUser_id(user.getId());
			this.ezs_orderformMapper.insert(orderform);
			
			goodscart.setOf_id(orderform.getId());
			this.ezs_goodscartMapper.updateByPrimaryKey(goodscart);
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_REQ_SUCCESS);
			mmp.put("Msg", "");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			mmp.put("ErrorCode", DictionaryCode.ERROR_WEB_PARAM_ERROR);
			mmp.put("Msg", "");
		}
		return mmp;
	}
	
}
