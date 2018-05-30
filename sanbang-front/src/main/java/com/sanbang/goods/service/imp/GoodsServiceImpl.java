package com.sanbang.goods.service.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_customized;
import com.sanbang.bean.ezs_customized_record;
import com.sanbang.bean.ezs_dict;
import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.bean.ezs_orderform;
import com.sanbang.goods.service.GoodsService;

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
	
	public int insertCart(ezs_goodscart goodsCart){
		int n ;
		n = ezs_goodscartMapper.insertSelective(goodsCart);
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
	
	public int insertOrder(ezs_orderform order){
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
	
}
