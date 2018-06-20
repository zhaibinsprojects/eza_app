package com.sanbang.buyer.service;

import com.mysql.fabric.xmlrpc.base.Array;

public interface CartCacheService {
/**
 * 购物车缓存
 * @return
 */
public void setCartCacheMethod(Array[] array,String userkey);	

/**
 * 得到购物车缓存
 * @return
 */
public String getCartCacheMethod(Array[] array,String userkey);	
}
