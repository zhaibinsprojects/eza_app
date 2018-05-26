package com.sanbang.seller.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_goods_cartography;
import com.sanbang.bean.ezs_goods_photo;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;
import com.sanbang.vo.userauth.AuthImageVo;

public interface SellerGoodsService {

	Map<String, Object> queryGoodsListBySellerId(Long sellerId, int status, String currentPage);

	ezs_goods queryGoodsInfoById(long id);

	List<ezs_accessory> queryPhotoById(Long goodsId);

	Result addGoodsInfo(Result result, ezs_user upi, HttpServletRequest request, HttpServletResponse response);

	Result pullOffShelvesById(Result result, long goodsId);

	Result updateGoodsInfoById(Result result, long goodsId, ezs_user upi, HttpServletRequest request, HttpServletResponse response);

	Result submitGoodsForAudit(Result result, long goodsId, HttpServletRequest request, HttpServletResponse response);

	List<ezs_accessory> queryCartographyById(Long goodsId);
	//查询货品属性
	String getGoodsProperty(Long propertyId);

}
