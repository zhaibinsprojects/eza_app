package com.sanbang.redis;

/**
 * @author zhangxiantao 2016年4月26日 描述：
 */
public interface RedisConstants {

	/**
	 * 下面是通用的int型返回值约定,错误值都是负数，禁用boolean型返回。
	 */
	int SUCCESS = 1;
	int NO_RETURN = -1;// 无数据
	int FAILURE = -2;// 程序异常

}
