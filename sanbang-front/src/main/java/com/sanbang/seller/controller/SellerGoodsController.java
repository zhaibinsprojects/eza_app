package com.sanbang.seller.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sanbang.bean.ezs_goods;
import com.sanbang.bean.ezs_user;
import com.sanbang.seller.service.SellerGoodsService;
import com.sanbang.utils.RedisUserSession;
import com.sanbang.utils.Result;
import com.sanbang.vo.DictionaryCode;

@Controller
public class SellerGoodsController {
	@Autowired
	SellerGoodsService sellerGoodsService;
	
	@RequestMapping("/queryGoodsList")
	public Object queryGoodsList(HttpServletRequest request, HttpServletResponse response){
		Result result = Result.failure();
		
		ezs_user upi=RedisUserSession.getLoginUserInfo(request);
		if(upi==null){
			result.setErrorcode(DictionaryCode.ERROR_WEB_SESSION_ERROR);
			result.setMsg("用户未登录");
			return result;
		}
		
		Long sellerId = upi.getId();
		
		List<ezs_goods> list = sellerGoodsService.queryGoodsListBySellerId(sellerId);
		
		
		
		return result;
	}
	
}
