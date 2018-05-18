package com.sanbang.seller.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;

public interface SellerGoodsService {

	List<ezs_goods> queryGoodsListBySellerId(Long sellerId, int status);

	ezs_goods queryGoodsInfoById(long id);

	List<ezs_accessory> queryPhotoById(Long goodsId);

	Result addGoodsInfo(Result result, ezs_user upi, HttpServletRequest request, HttpServletResponse response);

}
