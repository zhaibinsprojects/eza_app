package com.sanbang.seller.service;

import java.util.List;

import com.sanbang.bean.ezs_goods;

public interface SellerGoodsService {

	List<ezs_goods> queryGoodsListBySellerId(Long sellerId);

}
