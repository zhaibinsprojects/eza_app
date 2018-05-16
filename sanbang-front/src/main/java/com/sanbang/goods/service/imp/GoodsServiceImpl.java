package com.sanbang.goods.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_dvaluate;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goodscart;
import com.sanbang.goods.service.GoodsService;

/**
 * 货品相关处理
 * @author hanlongfei
 * 2018年05月12日
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{
	@Resource(name="ezs_goodsMapper")
	private com.sanbang.dao.ezs_goodsMapper ezs_goodsMapper;
	@Resource(name="ezs_dvaluateMapper")
	private com.sanbang.dao.ezs_dvaluateMapper ezs_dvaluateMapper;
	@Resource(name="ezs_goodscartMapper")
	private com.sanbang.dao.ezs_goodscartMapper ezs_goodscartMapper;
	
	/**
	 * 查询单个货品详情
	 * @param id
	 * @return
	 */
	public ezs_goods getGoodsDetail(long id){
		ezs_goods goods = new ezs_goods();
		goods = ezs_goodsMapper.selectByPrimaryKey(id);
		return goods;
	}
	
	public List<ezs_dvaluate> listForEvaluate(long id){
		List<ezs_dvaluate> list = new ArrayList();
		list  = ezs_dvaluateMapper.listForEvaluate(id);
		return list;
	}
	
	
	public int insertCart(ezs_goodscart goodsCart){
		int n ;
		n = ezs_goodscartMapper.insert(goodsCart);
		return n;
		
	}
	
	
}
