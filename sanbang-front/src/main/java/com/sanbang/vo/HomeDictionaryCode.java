package com.sanbang.vo;

public class HomeDictionaryCode {
	
	public static final String order_self_good = "ORDER_SELF_GOOD";// 自营商品订单
	
	public static final String sample_goods = "SAMPLE_GOODS";//撮合
	public static final String self_goods = "SELF_GOODS";// 自營商品
	
	public static final String order_sample_good = "ORDER_SAMPLE_GOOD";// 样品商品订单
	public static final String self_sample_goods = "SELF_SAMPLE_GOODS";// 自營樣品商品
	
	public static final String order_sample_match_good = "ORDER_SAMPLE_MATCH_GOOD";// 样品撮合商品订单
	public static final String order_match_good = "ORDER_MATCH_GOOD";// 撮合商品订单
	
	public static final String match_sample_goods = "MATCH_SAMPLE_GOODS";// 供應商樣品商品
	public static final String match_goods = "MATCH_GOODS";// 供應商商品
	/**
     * 查询热门城市异常
     */
    public static final int ERROR_HOME_HOTCITY_FAIL = 101201;
    /**
     * 该地址节点无子节点
     */
    public static final int ERROR_HOME_HOTCITY_NOCHILDREN = 101203;
    /**
     * 页码越界
     */
    public static final int ERROR_HOME_PAGE_FAIL = 101202;
    /**
     * 用户未登录
     */
    public static final int ERROR_HOME_UN_LOGIN = 101203;
    /**
     * 查询类型有误
     */
    public static final int ERROR_HOME_KIND_ERROR = 101204;
    //库存不足
    public static final int ERROR_HOME_INVENTORY_ERROR = 101205;
}
