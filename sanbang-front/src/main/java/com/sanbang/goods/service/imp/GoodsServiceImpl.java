package com.sanbang.goods.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_documentshare;
import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
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
	
	
	/**
	 * 查询单个货品详情
	 * @param id
	 * @return
	 */
	
	public ezs_goods getGoodsDetail(Long id){
		ezs_goods goods = new ezs_goods();
		goods = ezs_goodsMapper.selectByPrimaryKey(id);
		return goods;
	}
	
	public List<ezs_dvaluate> listForEvaluate(Long id){
		List<ezs_dvaluate> list = new ArrayList();
		list  = ezs_dvaluateMapper.listForEvaluate(id);
		return list;
	}
	
	public int insertCart(ezs_goodscart goodsCart){
		int n ;
		n = ezs_goodscartMapper.insert(goodsCart);
		return n;
		
	}
	
	public int updateCollect(Long id){
		int n = ezs_documentshareMapper.updateCollect(id);
		return n;
	}
	
	public int insertCollect(Long id){
		ezs_documentshare share = new ezs_documentshare();
		share.setGood_id(id);
		int n = ezs_documentshareMapper.insert(share);
		return n;
	}
	
	public int insertOrder(ezs_orderform order){
		int n = ezs_orderformMapper.insert(order);
		return n;
	}
	//同类货品
	public List<ezs_goods> listForGoods(Long id){
		List<ezs_goods> list = new ArrayList();
		list = ezs_goodsMapper.listForGoods(id);
		return list;
	}
	
	/**
	 * 自营，地区、品类筛选
	 * @param area
	 * @param type
	 * @return
	 */
	public List listByAreaAndType(String area,String type){
		List<ezs_goods> list = new ArrayList();
		list = ezs_goodsMapper.listByAreaAndType(area,type);
		return list;
	}
	/**
	 * 其他筛选
	 * @param terms	字符串数组
	 * @return
	 */
	public List listByOthers(String[] terms){
		List<ezs_goods> list = new ArrayList();
		for(String term : terms){
			
			
		}
		list = ezs_goodsMapper.listByOthers(terms);
		return list;
	}
	
	
	
}
