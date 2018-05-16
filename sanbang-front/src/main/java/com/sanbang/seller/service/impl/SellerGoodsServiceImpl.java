package com.sanbang.seller.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanbang.bean.ezs_goods;
import com.sanbang.dao.ezs_goodsMapper;
import com.sanbang.seller.service.SellerGoodsService;
@Service
public class SellerGoodsServiceImpl implements SellerGoodsService {
	
	@Autowired
	ezs_goodsMapper goodsMapper;
	
	@Override
	public List<ezs_goods> queryGoodsListBySellerId(Long sellerId) {
		
		return goodsMapper.selectGoodsListBySellerId(sellerId);
	}
	
	
}
