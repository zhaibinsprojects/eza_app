package com.sanbang.index.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_class;
import com.sanbang.dao.ezs_goods_classMapper;
import com.sanbang.index.service.GoodsClassService;

@Service
public class GoodsClassServiceImpl implements GoodsClassService {
	
	@Autowired
	private ezs_goods_classMapper goodClassMapper;

	@Override
	public List<ezs_goods_class> queryGoodsClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ezs_goods_class queryByClassId(Long id) {
		// TODO Auto-generated method stub
		return this.goodClassMapper.selectByPrimaryKey(id);
	}

	@Override
	public ezs_goods_class queryByGoods(ezs_goods goods) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ezs_goods_class> queryAllGoodsClass() {
		List<ezs_goods_class> eslist = this.goodClassMapper.selectAllGoodClass();
		return eslist;
	}

}
