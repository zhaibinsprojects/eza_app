package com.sanbang.utils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.sanbang.redis.RedisResult;
import com.sanbang.redis.RedisTool;

/**
 * 
 * @author zhangxiantao 2016年3月12日
 *
 */
public class RedisUtils {
	/** redis操作类*/
	private static RedisTemplate<String, Object> redisTemplate;
	
	/** 日志 */
	private static Logger logger = Logger.getLogger(RedisUtils.class.getName());
	
	private static JsonRedisSeriaziler jsonSeriaziler=new JsonRedisSeriaziler();
	
	/**
	 * 设置redis的值
	 * @param key 键
	 * @param value 值
	 * @param timeOut 失效时间（秒）
	 * @throws Exception
	 */
	public static RedisResult<?> set(String key, Object value, Long timeOut){
		try {
			ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
			value=jsonSeriaziler.seriazileAsString(value);
			if(null == timeOut || 0 == timeOut){
				valueOperations.set(key, value);
			}else{
				valueOperations.set(key, value, timeOut, TimeUnit.SECONDS);
			}
			return RedisTool.buildSuccessResult(key);
		} catch (Exception e) {
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	
	/**
	 * 获取redis对应key的值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static synchronized RedisResult<?> get(String key,Class<?> clazz){
		try {
			ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
			Object value = valueOperations.get(key);
			if(StringUtils.isEmpty(value)) {
				return RedisTool.buildFailResult("the key is empty!");
			}
            value=jsonSeriaziler.deserializeAsObject((String)value, clazz);
			return RedisTool.buildSuccessResult(value);
		} catch (Exception e) {
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	/**
	 * 获取redis 模糊key
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static synchronized RedisResult<?> getMoHu(String pattern){
		try {
			Set<String> sets=redisTemplate.keys(pattern);
			if(StringUtils.isEmpty(sets)) {
				return RedisTool.buildFailResult("the key is empty!");
			}
			return RedisTool.buildSuccessResult(sets);
		} catch (Exception e) {
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	
	/**
	 *  获取具体类型
	 * @param key
	 * @param valueTypeRef
	 * @return
	 */
	public static synchronized RedisResult<?> get(String key ,TypeReference<?> valueTypeRef){
		try {
			ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
			Object value = valueOperations.get(key);
			if(StringUtils.isEmpty(value)) {
				return RedisTool.buildFailResult("the key is empty!");
			}
			value=jsonSeriaziler.deserializeAsObjectBean((String)value, valueTypeRef);
			return RedisTool.buildSuccessResult(value);
		} catch (Exception e) {
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}

	/**
	 * 根据key删除redis对应值
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static RedisResult<?> del(String key){
		try {
			if (StringUtils.isEmpty(key)) {
				return RedisTool.buildFailResult("input key is empty!");
			}
			redisTemplate.delete(key);
			return RedisTool.buildSuccessResult(key);
		} catch (Exception e) {
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	
	/**
	 * 设置redis List值(先删掉原来的key,在增加)
	 * @param key
	 * @param valueList
	 * @return
	 * @throws Exception
	 */
	public static RedisResult<?> setList(String key, List<?> valueList){
		try {
			del(key);
			ListOperations<String, Object> listOperations = redisTemplate.opsForList();
			//存List的三种方式
			/*for (Object value : valueList) {
				listOperations.rightPush(key, value);
			}*/
			listOperations.rightPushAll(key, valueList.toArray());
			//会多包一层[]
			//listOperations.rightPushAll(key, valueList);
			return RedisTool.buildSuccessResult(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}

	/**
	 * 设置redis List值(先删掉原来的key,在增加)
	 * @param key
	 * @param valueList
	 * @return
	 * @throws Exception
	 */
	public static RedisResult<?> setListSagment(String key, Object value){
		try {
			ListOperations<String, Object> listOperations = redisTemplate.opsForList();
			value=jsonSeriaziler.seriazileAsString(value);
			listOperations.rightPush(key, value);
			return RedisTool.buildSuccessResult(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}

	/**
	 * 获取list
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static RedisResult<?> getListAll(String key){
		try {
			ListOperations<String, Object> listOperations = redisTemplate.opsForList();
			return RedisTool.buildSuccessResult(listOperations.range(key, 0, -1));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	
	public static RedisResult<?> getList(String key){
		try {
			ListOperations<String, Object> listOperations = redisTemplate.opsForList();
			return RedisTool.buildSuccessResult(listOperations.range(key, 0, -1));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	
	/**
	 * 设置redis Hash值
	 * @param key
	 * @param valueList
	 * @return
	 * @throws Exception
	 */
	public static RedisResult<?> setHash(String key, String sekey, Object value){
		try {
			HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
			hashOperations.put(key,sekey,value);
			return RedisTool.buildSuccessResult(key);
		} catch (Exception e) {
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}

	/**
	 * 获取hash
	 */
	public static RedisResult<?> getHash(String key, String sekey){
		try {
			HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
			return RedisTool.buildSuccessResult(hashOperations.get(key,sekey));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	
	/**
	 * 删除hash
	 */
	public static RedisResult<?> delHash(String key, String sekey){
		try {
			HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
			hashOperations.delete(key,sekey);
			return RedisTool.buildSuccessResult(null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	/**
	 * 获取某个键的剩余生命时间 单位 秒
	 * @param key
	 * @return
	 */
	public static RedisResult<?> getExpir(String key){
		try {
			Long expir = redisTemplate.getExpire(key, TimeUnit.SECONDS);
			if(expir!=0)
			return RedisTool.buildSuccessResult(expir);
			else  return RedisTool.buildNoResult("没有此key或者已经过期");
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	/**
	 * 设置某个键的有效期
	 * @param key
	 * @return
	 */
	public static RedisResult<?> setExpireln(String key,Long sendcodeexpir){
		try {
			Boolean expirR = redisTemplate.expire(key, sendcodeexpir, TimeUnit.SECONDS);
			if(expirR){
				return RedisTool.buildSuccessResult(key);
			}else{
				return RedisTool.buildFailResult("设置有效期失败，可能不存在key");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("操作redis数据时出现异常,异常信息 :"+e.getMessage());
			return RedisTool.buildFailResult("操作redis数据时出现异常,异常信息 :"+e.getMessage());
		}
	}
	
	public static RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public static void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		RedisUtils.redisTemplate = redisTemplate;
	}
	
}
