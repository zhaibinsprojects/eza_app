package com.sanbang.goods.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
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

}
