package com.sanbang.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.sanbang.utils.CastUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

/**
 * @author zhangxiantao 2016年4月29日 描述：
 */
@SuppressWarnings({"rawtypes", "unused"})
public class RedisHelper {
	
	private static Logger logger = Logger.getLogger(RedisHelper.class.getName());

	/**
	 * 连接池 非切片连接池
	 */
	protected static JedisPool jedisPool;
	protected static Jedis jedis;// 非切片额客户端连接

	/**
	 * 同步锁
	 */
	private final static Object objSync = new Object();

	private RedisHelper() {
		super();
	}

	/**
	 * 获取Redis链接
	 *
	 * @return Jedis Object
	 */
	static {
		jedis = maybeInitAndGet();
	}

	/**
	 * @author zhangxiantao 2016年4月25日 描述：初始化切片
	 */
	protected static void createAndConnectPool() {
		if (jedisPool == null) {
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setMaxIdle(JedisConfig.poolMaxIdle);
			poolConfig.setMaxTotal(JedisConfig.poolMaxTotal);
			poolConfig.setMinIdle(JedisConfig.poolMinIdle);
			poolConfig.setTestOnBorrow(false);
			jedisPool = new JedisPool(poolConfig, JedisConfig.masterHost, JedisConfig.masterPort, JedisConfig.poolMaxWaitMillis, JedisConfig.masterPwd);
		}
	}

	/**
	 * @author zhangxiantao 2016年4月25日 描述： 切片维护
	 * @return
	 */
	protected static Jedis maybeInitAndGet() {
		if (jedisPool == null) {
			synchronized (objSync) {
				createAndConnectPool();
			}
		}
		// 获取可用的链接
		Jedis j = getWorkingResource();
		if (j != null) {
			return j;
		}
		synchronized (objSync) {
			System.out.println("超过50%的链接失效，重新初始化连接池");
			jedisPool = null;
			for (int i = 0; i < JedisConfig.reconnectRetryCount; i++) {
				shutdownPool();
				createAndConnectPool();
				if (jedisPool != null) {
					Jedis jd = getWorkingResource();

					if (jd != null) {
						return jd;
					}
				}
				// 等待获取链接
				if (i < JedisConfig.reconnectRetryCount - 1) {
					try {
						Thread.sleep(JedisConfig.reconnectRetryWaittime);
					} catch (Exception e) {
					}
				}
			}
		}
		return null;
	}

	protected static void shutdownPool() {
		if (jedisPool == null) {
			return;
		}
		jedisPool.destroy();
		jedisPool = null;
	}

	/**
	 * 获取可用的链接，如果没有返回null
	 *
	 * @return Jedis Object
	 */
	@SuppressWarnings("deprecation")
	protected static Jedis getWorkingResource() {
		for (int i = 0; i < JedisConfig.failedResourceBeforeReconnect; i++) {
			Jedis j = jedisPool.getResource();

			if (j.isConnected()) {
				return j;
			} else {
//				j.close();
				jedisPool.returnBrokenResource(j);
			}
		}

		return null;
	}
	
	/**
	 * 返回Jedis链接给连接池
	 *
	 * @param jedis
	 */
	public static void returnRes(Jedis jedis) {
		if (jedisPool != null) {
			jedis.close();
//			jedisPool.returnResource(jedis);
		}
	}

	/********************************************* Operator Start ************************************************/

	/**** ========================== key START ========================== ***/
	/**
	 * @author zhangxiantao 2016年4月29日 描述：redis--> key del
	 * @param key
	 * @return
	 */
	public static RedisResult del(String key) {
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(key)) {
				return RedisTool.buildFailResult("the key needed delete is empty!");
			}
			// 执行删除
			long result = jedis.del(key);
			return RedisTool.buildSuccessResult(result);	
		}catch(Exception e){
			logger.error("删除key时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
	}

	/**
	 * @author zhangxiantao 2016年4月29日 描述：redis--> keys mdel 删除多个key
	 * @param keys
	 * @return
	 */
	public static RedisResult mdel(String... keys) {
		try{
			jedis = maybeInitAndGet();
			if (keys == null || keys.length < 1) {
				return RedisTool.buildFailResult("the key needed delete is empty!");
			}
			long result = jedis.del(keys);
			return RedisTool.buildSuccessResult(result);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月29日 描述：redis--> vagueKey 模糊匹配 删除多个key
	 * @param vagueKey
	 * @return
	 */
	public static RedisResult vagueDel(String vagueKey) {
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(vagueKey)) {
				return RedisTool.buildFailResult("The key needed delete is empty!");
			}
			//查询出所有的模糊匹配的，所有的key
			Set<String> keySet = jedis.keys(vagueKey);
			String[] keysArray = keySet.toArray(new String[]{});
			if (keySet == null || keySet.size() < 1) {
				return RedisTool.buildFailResult("The key needed delete is not exist!");
			}
			/** 进行删除 **/
			return mdel(keysArray);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}
		
	}

	/**** ========================== key START ========================== ***/
	/**** ========================== String START ========================== ***/

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> String get
	 * @param key
	 * @return
	 */
	public static synchronized RedisResult get(String key) {
		try{
			jedis = maybeInitAndGet();
			String redisRtn = jedis.get(key);
			if (redisRtn == null) {
				return RedisTool.buildNoResult("The key named '" + key
						+ "' is not exists in redis!");
			}
			//logger.debug("redisRtn     " + redisRtn);
			return RedisTool.buildSuccessResult(redisRtn);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}finally{
			returnRes(jedis);
		}
		
	}
	
	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> String mget
	 * @param keys
	 * @return
	 */
	public static RedisResult mget(String... keys) {
		try{
			jedis = maybeInitAndGet();
			if (null == keys || keys.length < 1) {
				return RedisTool.buildFailResult("The key needed query is empty!");
			}
			List<String> resultList = jedis.mget(keys);
			//将结果集返回
			return RedisTool.buildSuccessResult(resultList);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> String mget
	 * @param vagueKey
	 * @return
	 */
	public static RedisResult vagueGet(String vagueKey) {
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(vagueKey)) {
				return RedisTool.buildFailResult("The key needed query is empty!");
			}
			// 获取所有的key
			Set<String> keySet = jedis.keys(vagueKey);
			if (keySet == null || keySet.size() < 1) {
				return RedisTool
						.buildFailResult("The key needed query is not exist!");
			}
			String[] keysArray = keySet.toArray(new String[]{});
			List<String> resultList = jedis.mget(keysArray);
			//将结果集返回
			return RedisTool.buildSuccessResult(resultList);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
		
	}
	
	/**
	 * @author zhangxiantao
	 * 2016年4月29日 描述：模糊查询redis,返回key-value map  (性能差，慎用)
	 * @param vagueKey
	 * @return
	 */
	public static RedisResult vagueGetMap(String vagueKey) {
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(vagueKey)) {
				return RedisTool.buildFailResult("The key needed query is empty!");
			}
			Map<String, String> returnMap = new HashMap<>();
			// 获取所有的key
			Set<String> keySet = jedis.keys(vagueKey);
			if (keySet == null || keySet.size() < 1) {
				return RedisTool
						.buildFailResult("The key needed query is not exist!");
			}
			for(String key : keySet){
				returnMap.put(key, jedis.get(key));
			}
			//将结果集返回
			return RedisTool.buildSuccessResult(returnMap);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}
	
	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> String set
	 * @param key
	 * @param value
	 * @return
	 */
	public static RedisResult set(String key, String value) {
		try{
			jedis = maybeInitAndGet();
			if (value == null) {
				return RedisTool.buildFailResult("SerializeTool '" + key
						+ "' data '" + value + "' is fail");
			}
			//logger.debug("key " + key);
			String redisRtn = jedis.set(key, value);
			if (!redisRtn.equals("OK")) {
				return RedisTool.buildFailResult("redis set '" + key + "' data '"
						+ value + "' is fail");
			}
			return RedisTool.buildSuccessResult("OK");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月29日 描述：设置String的值，并设定过期时间，单位 为 秒
	 * @param key
	 * @param value
	 * @param seconds
	 * @return
	 */
	public static RedisResult set(String key, String value, int seconds) {
		try{
			jedis = maybeInitAndGet();
			if (value == null) {
				return RedisTool.buildFailResult("SerializeTool '" + key
						+ "' data '" + value + "' is fail");
			}
			//logger.debug("key    " + key);
			String redisRtn = jedis.setex(key, seconds, value);
			if (!redisRtn.equals("OK")) {
				return RedisTool.buildFailResult("redis set '" + key + "' data '"
						+ value + "' is fail");
			}
			return RedisTool.buildSuccessResult("OK");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> String mset
	 * @param key
	 * @return
	 */
	public static RedisResult mset(String key) {
		return null;
	}

	/**** ========================== String END ========================== ***/
	/**** ========================== set START ========================== ***/

	/**
	 * @author zhangxiantao 2016年4月28日 描述：Redis set 操作，随机弹出Set中的数据
	 * @param key
	 * @return
	 */
	public static RedisResult randomUsed(String key) {
		try{
			jedis = maybeInitAndGet();
			String redisRtn = jedis.spop(key);
			if (redisRtn == null) {
				return RedisTool.buildNoResult("The key named '" + key
						+ "' is no data in redis!");
			}
			//logger.debug("redisRtn     " + redisRtn);
			return RedisTool.buildSuccessResult(redisRtn);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月28日 描述：Redis 向Set中添加数据
	 * @param key
	 * @param empNoList
	 * @return
	 */
	public static RedisResult sadd(String key, List<String> empNoList) {
		try{
			jedis = maybeInitAndGet();
			////logger.info(DateUtil.getTodayYYYYMMDD_HHMMSS() + "   redis generate empno start");
			String[] members = new String[empNoList.size()];
			//转化为数组
			jedis.sadd(key, empNoList.toArray(members));
			//logger.info(DateUtil.getTodayYYYYMMDD_HHMMSS() + "   redis generate empno end");
			Set<String> set = jedis.smembers(key);
			if (set.size() == empNoList.size()) {
				//logger.debug(" all empno insert succ");
			}
			return RedisTool.buildSuccessResult("OK");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
		
	}


	/**** ========================== set END ========================== ***/
	/**** ========================== hash START ========================== ***/

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> hash set 暂时不开放
	 * @param key
	 * @param dto
	 *            实体类对象
	 * @return
	 */
	private static RedisResult hset(String key, Object dto) {

		if (dto == null) {
			return RedisTool.buildFailResult("The data needed save is empty!");
		}

		return RedisTool.buildSuccessResult("OK");
	}

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> hash set
	 * @param key
	 * @param dataBus
	 *            数据Map 集合
	 * @return
	 */
	public static RedisResult hset(String key, Map<String, String> dataBus) {
		try{
			jedis = maybeInitAndGet();
			if (dataBus == null || dataBus.isEmpty() || dataBus.size() < 1) {
				return RedisTool.buildFailResult("The data needed save is empty!");
			}
			//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [ " + key + " ] , dataBus is [ " + JSONArray.toJSONString(dataBus) + " ]");

			Map<String, String> tempMap = new HashMap<>();
			Set<String> fields = dataBus.keySet();
			for (String field : fields) {
				if (field == null) {
					//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [ " + key + " ] , field  is null! ");
				}
				String value = CastUtil.castString(dataBus.get(field));
				tempMap.put(field, value);
			}
			jedis.hmset(key, tempMap);
			return RedisTool.buildSuccessResult("OK");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> hash set
	 * @param key
	 * @param dataBus
	 *            数据Map 集合
	 * @return
	 */
	public static RedisResult hset(String key, Map<String, String> dataBus,
			int seconds) {
		try{
			jedis = maybeInitAndGet();
			if (dataBus == null || dataBus.isEmpty() || dataBus.size() < 1) {
				return RedisTool.buildFailResult("The data needed save is empty!");
			}
			//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [ " + key + " ] , dataBus is [ " + JSONArray.toJSONString(dataBus) + " ]");

			Map<String, String> tempMap = new HashMap<>();
			Set<String> fields = dataBus.keySet();
			for (String field : fields) {
				if (field == null) {
					//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [ " + key + " ] , field  is null! ");
				}
				String value = CastUtil.castString(dataBus.get(field));
				tempMap.put(field, value);
			}
			// 向redis 存入值
			jedis.hmset(key, tempMap);
			// 设置改KEY的过期时间
			if (seconds > 0) {
				jedis.expire(key, seconds);
			}
			return RedisTool.buildSuccessResult("OK");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
	}

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> hash set
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public static RedisResult hset(String key, String field, String value) {
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(key)) {
				return RedisTool
						.buildFailResult("The data key needed save is empty!");
			}
			if (StringUtils.isEmpty(field)) {
				return RedisTool
						.buildFailResult("The data field needed save is empty!");
			}
			//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [" + key + "] , dataBus is [ " + field + ":" + value + " ] ");
			jedis.hset(key, field,value);
			return RedisTool.buildSuccessResult("OK");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月29日 描述：redis--> hash set 带有过期时间
	 * @param key
	 * @param field
	 * @param value
	 * @param seconds
	 * @return
	 */
	public static RedisResult hset(String key, String field, String value,
			int seconds) {
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(key)) {
				return RedisTool
						.buildFailResult("The data key needed save is empty!");
			}
			if (StringUtils.isEmpty(field)) {
				return RedisTool.buildFailResult("The data field needed save is empty!");
			}
			//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [" + key + "] , dataBus is [ " + field + ":" + value + " ] ");

			//System.out.println(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [" + key + "] , dataBus is [ " + field + ":" + value + " ] ");

			jedis.hset(key, field, value);
			// 设置改KEY的过期时间
			if (seconds > 0) {
				jedis.expire(key.getBytes(), seconds);
			}
			return RedisTool.buildSuccessResult("OK");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
		
	}

	/**
	 * @author zhangxiantao 2016年4月28日 描述：redis--> hash et
	 * @param key
	 * @param field
	 * @return
	 */
	public static RedisResult hget(String key, String field) {
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(key)) {
				return RedisTool
						.buildFailResult("The data key needed save is empty!");
			}
			if (StringUtils.isEmpty(field)) {
				return RedisTool.buildFailResult("The data field needed save is empty!");
			}
			//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash add data, key is [" + key + "] , field is [ " + field + " ] ");
			String rtnObj = jedis.hget(key, field);
			//logger.debug(DateUtil.getTodayYYYYMMDD_HHMMSS() + "redis hash get data, key is [" + key + "] , field is [ " + field + " ] is succ!");
			return RedisTool.buildSuccessResult(CastUtil.castString(rtnObj));
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
		
	}

	/**** ========================== hash END ========================== ***/
	/**** ========================== list START ========================== ***/
	/**** ========================== list END ========================== ***/
	/**** ========================== zset START ========================== ***/
	/**** ========================== zset END ========================== ***/
	
	/**
	 * 
	 * @author liyong
	 * 2016年5月10日 描述：带事务的批量添加 主要是给活动用的
	 * @param map 保存的key value
	 * @param seconds
	 * @return
	 */
	public static RedisResult transationSet(Map<String,String> map,int seconds){
		try{
			jedis = maybeInitAndGet();
			Transaction tx = jedis.multi();
			Set<String> keys = map.keySet();
			for(String key:keys){
				tx.setex(key, seconds, map.get(key));
			}
			List<Object> result =  tx.exec();
			if(result==null||!result.get(0).equals("OK")){
				//logger.debug("redis 批量添加失败");
				throw new RuntimeException("redis 批量添加失败");
			}
			return RedisTool.buildSuccessResult(true);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}
	
	/**
	 * 
	 * @author liyong
	 * 2016年5月10日 描述：批量模糊匹配 批量删除 主要是给活动用的
	 * @param list 模糊匹配的主键
	 * @return
	 */
	public static RedisResult vagueDels(List<String> list){
		
		try{
			jedis = maybeInitAndGet();
			if (list == null || list.size() < 1) {
				return RedisTool.buildFailResult("参数不可以为空!");
			}
			Set<String> keys  = new HashSet<>();
			for(String id :list){
				keys.addAll(jedis.keys(id)) ;
			}
			if (keys == null || keys.size() < 1) {
				return RedisTool.buildSuccessResult("The key needed delete is not exist!");
			}
			String[] keysArray = new String[keys.size()];
			/** 进行删除 **/
			long result = jedis.del(keys.toArray(keysArray));
			return RedisTool.buildSuccessResult(result);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}
	
	/**
	 * 
	 * @author liyong
	 * 2016年6月3日 描述：单个模糊匹配 批量删除 主要是给活动用的 
	 * 当从缓存中没有找到key,说明缓存中没有需要删除的 也是正常的
	 * @param vagueKey 模糊匹配的主键
	 * @return
	 */
	public static RedisResult vagueDels(String vagueKey){
		
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(vagueKey)) {
				return RedisTool.buildFailResult("The vagueKey needed is empty!");
			}
			//查询出所有的模糊匹配的，所有的key
			Set<String> keySet = jedis.keys(vagueKey);
			
			if (keySet == null || keySet.size() < 1) {//当从缓存中没有找到key,说明缓存中没有需要删除的 也是正常的
				return RedisTool.buildSuccessResult("The key needed delete is not exist!");
			}
			String[] keysArray = keySet.toArray(new String[]{});
			
			/** 进行删除 **/
			long result = jedis.del(keysArray);
			return RedisTool.buildSuccessResult(result);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}
	
	/**
	 * 
	 * @author liyong
	 * 2016年5月11日 描述：根据带*的key 查出所有的key
	 * @param vagueKey
	 */
	public static RedisResult getkeysByVagueKey(String vagueKey){
		
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(vagueKey)) {
				return RedisTool.buildFailResult("The vagueKey needed  is empty!");
			}
			//查询出所有的模糊匹配的，所有的key
			Set<String> keySet = jedis.keys(vagueKey);
			if (keySet == null || keySet.size() < 1) {
				return RedisTool.buildFailResult("The key needed is not exist!");
			}
			return RedisTool.buildSuccessResult(keySet);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
		
	}
	/**
	 
	 * @author liyong
	 * 2016年5月12日 描述：更新缓存中的key 基本不影响过期时间
	 * @param key 
	 * @param value
	 * @return
	 */
	public static RedisResult updateKeyOnlyValue(String key,String value){
		try{
			jedis = maybeInitAndGet();
			if (StringUtils.isEmpty(key)) {
				return RedisTool.buildFailResult("The key needed is empty!");
			}
			Boolean flag = jedis.exists(key);
			if(flag){
				Long ltime = jedis.pttl(key);
				if(ltime>0){
					String result = jedis.setex(key, (int)(ltime/1000)+1, value);//过期时间误差在一秒之内
					if("OK".equals(result)){
						//logger.info(key+"key 修改成功");
						return RedisTool.buildSuccessResult(result);
					}
					//logger.error(key+"key 修改失败" +result);
					throw new RuntimeException(result);//只有抛出异常了才会有  mysql的回滚
				}else{
					String result = jedis.set(key, value);
					if("OK".equals(result)){
						//logger.info(key+"key 修改成功");
						return RedisTool.buildSuccessResult(result);
					}
					//logger.error(key+"key 修改失败" +result);
					throw new RuntimeException(result);//只有抛出异常了才会有  mysql的回滚
				}
			}else{
				//logger.info(key+"key is not exist" );
				return RedisTool.buildFailResult("The key needed is not exist!");
			}
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
//			return RedisTool.buildFailResult("操作redis数据时出现异常");
		}finally{
			returnRes(jedis);
		}
	}
	
	/**
	 * 判断指定key是否存在
	 * 
	 * @param key
	 * @return
	 * 
	 * @author yangbin
	 */
	public static RedisResult existsKey(String key) {
		try{
			jedis = maybeInitAndGet();
			return RedisTool.buildSuccessResult(jedis.exists(key));
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			throw new RuntimeException(e);
		}finally{
			returnRes(jedis);
		}
	}
	
	
	/**
	 * @author zhangxiantao
	 * 2016年6月29日 描述：自增
	 * @param key
	 * @return
	 */
	public static RedisResult rIncr(String key) {
		try{
			if (StringUtils.isEmpty(key)) {
				return RedisTool.buildFailResult("The key needed is empty!");
			}
			jedis = maybeInitAndGet();
			long result = jedis.incr(key);
			return RedisTool.buildSuccessResult(result);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}finally{
			returnRes(jedis);
		}
	}
	
	/**
	 * @author zhangxiantao
	 * 2016年6月28日 描述：自增
	 * @param key
	 * @param increment
	 * @return
	 */
	public static RedisResult rIncr(String key, int increment) {
		try{
			if (StringUtils.isEmpty(key)) {
				return RedisTool.buildFailResult("The key needed is empty!");
			}
			jedis = maybeInitAndGet();
			if(increment > 0){
				long result = jedis.incrBy(key, increment);
				return RedisTool.buildSuccessResult(result);
			}
			return RedisTool.buildSuccessResult("increment cannot less than 1 ");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}finally{
			returnRes(jedis);
		}
	}
	
	/**
	 * @author zhangxiantao
	 * 2016年6月29日 描述：自减
	 * @param key
	 * @return
	 */
	public static RedisResult rDecr(String key) {
		try{
			if (StringUtils.isEmpty(key)) {
				return RedisTool.buildFailResult("The key needed is empty!");
			}
			jedis = maybeInitAndGet();
			long result = jedis.decr(key);
			return RedisTool.buildSuccessResult(result);
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}finally{
			returnRes(jedis);
		}
	}
	
	/**
	 * @author zhangxiantao
	 * 2016年6月28日 描述：自减
	 * @param key
	 * @param increment
	 * @return
	 */
	public static RedisResult rDecr(String key, int increment) {
		try{
			if (StringUtils.isEmpty(key)) {
				return RedisTool.buildFailResult("The key needed is empty!");
			}
			jedis = maybeInitAndGet();
			if(increment > 0){
				long result = jedis.decrBy(key, increment);
				return RedisTool.buildSuccessResult(result);
			}
			return RedisTool.buildSuccessResult("increment cannot less than 1 ");
		}catch(Exception e){
			//logger.error("操作redis数据时出现异常，异常信息  ::: "+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}finally{
			returnRes(jedis);
		}
	}
	
}
