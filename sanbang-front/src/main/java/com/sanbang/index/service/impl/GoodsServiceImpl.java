package com.sanbang.index.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.index.service.GoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
	
	@Autowired
	private ezs_goodsMapper goodsMapper;

	@Override
	public ezs_goods queryById(Long id) {
		// TODO Auto-generated method stub
		return this.goodsMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ezs_goods> queryByName(String name) {
		return null;
	}

	@Override
	public void addGoods(ezs_goods goods) {
		// TODO Auto-generated method stub
		this.goodsMapper.insert(goods);

	}

	@Override
	public void modifyGoodsById(ezs_goods goods) {
		// TODO Auto-generated method stub
		this.goodsMapper.updateByPrimaryKeySelective(goods);
	}

	@Override
	public void dropGoodsById(ezs_goods goods) {
		// TODO Auto-generated method stub
		this.goodsMapper.deleteByPrimaryKey(goods.getId());
	}

}
