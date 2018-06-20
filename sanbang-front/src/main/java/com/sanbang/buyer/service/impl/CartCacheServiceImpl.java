package com.sanbang.buyer.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.sanbang.buyer.service.CartCacheService;
import com.sanbang.redis.RedisConstants;
import com.sanbang.redis.RedisResult;
import com.sanbang.utils.RedisUtils;

@Service("cartCacheService")
public class CartCacheServiceImpl implements CartCacheService{

	//购物车标识
	@Value("${consparam.user.cart}")
	private String usercart;
	
	@Override
	public void setCartCacheMethod(Array[] array,String userkey) {
		if(array.length>0){
			RedisUtils.del(userkey+usercart);
			RedisUtils.set(userkey+usercart, array.toString(), (long)0);
		}
	}

	@Override
	public String getCartCacheMethod(Array[] array, String userkey) {
		@SuppressWarnings("unchecked")
		RedisResult<String> results = (RedisResult<String>) RedisUtils.get(userkey+usercart, Array.class);
		if (results.getCode() == RedisConstants.SUCCESS) {
			String sets = results.getResult();
			return sets;
		}
		return "";
	}
	
}
