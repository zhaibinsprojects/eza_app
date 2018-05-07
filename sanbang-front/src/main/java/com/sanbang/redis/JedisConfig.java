package com.sanbang.redis;

import com.sanbang.utils.PropertiesSource;

/**
 * @author zhangxiantao 2016年4月25日 描述：redis配置文件
 */
public interface JedisConfig {

	// 主节点配置端口
	Integer masterPort = Integer.parseInt(PropertiesSource.getProperty("redis.port"));
	String masterHost = PropertiesSource.getProperty("redis.ip");
	String masterPwd = PropertiesSource.getProperty("redis.pwd");
	// 最大连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
	Integer poolMaxTotal = Integer.parseInt(PropertiesSource.getProperty("redis.pool.maxTotal"));;
	// 最大空闲连接数, 应用自己评估，不要超过AliCloudDB for Redis每个实例最大的连接数
	Integer poolMaxIdle = Integer.parseInt(PropertiesSource.getProperty("redis.pool.maxIdle"));
	Integer poolMinIdle = Integer.parseInt(PropertiesSource.getProperty("redis.pool.minIdle"));;
	Integer poolMaxWaitMillis = Integer.parseInt(PropertiesSource.getProperty("redis.pool.maxWait"));
	boolean poolTestOnBorrow = Boolean.parseBoolean(PropertiesSource.getProperty("redis.pool.testOnBorrow"));
	// 在放弃或者重新初始化链接池之前，获取链接的次数
	Integer failedResourceBeforeReconnect = poolMaxTotal / 2 + 1;
	// 重新链接重试次数
	Integer reconnectRetryCount = Integer.parseInt(PropertiesSource.getProperty("redis.pool.reconnectRetryCount"));
	// 每次重新链接等待时间（毫秒）
	Integer reconnectRetryWaittime = Integer.parseInt(PropertiesSource.getProperty("redis.pool.reconnectRetryWaittime"));;
}
