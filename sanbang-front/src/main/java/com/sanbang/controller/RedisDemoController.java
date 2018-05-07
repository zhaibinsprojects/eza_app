package com.sanbang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sanbang.redis.RedisHelper;
import com.sanbang.redis.RedisResult;

/**
 * Demo
 * 
 * @author zhangxiantao 2016-9-2
 */
@Controller
@RequestMapping("/admin")
public class RedisDemoController {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@RequestMapping("/setRedis")
	public String setRedis() throws Exception {
		try {
			ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
	        opsForValue.set("hello", "hello world");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/index";
	}
	
	@RequestMapping("/getRedis")
	@ResponseBody
	public String getRedis() throws Exception {
		ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
		opsForValue.get("hello");
		return opsForValue.get("hello").toString();
	}
	
	@RequestMapping("/set")
	@ResponseBody
	public String set() throws Exception {
		RedisHelper.set("bb", "aa");
		return "aa";
	}
	
	@RequestMapping("/get")
	@ResponseBody
	public String get() throws Exception {
		RedisResult<?> redisResult = RedisHelper.get("bb");
		return redisResult.getResult().toString();
	}
}
