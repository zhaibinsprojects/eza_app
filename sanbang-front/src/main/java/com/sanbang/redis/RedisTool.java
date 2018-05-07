package com.sanbang.redis;

/**
 * @author zhangxiantao 2016年4月26日 描述：
 */
public class RedisTool {
	
	/**
	 * @author zhangxiantao
	 * 2016年4月26日 描述：执行成功
	 * @param obj
	 * @return
	 */
	public static RedisResult<?> buildSuccessResult(Object obj) {
		return buildResult(RedisConstants.SUCCESS, null, obj);
	}
	
	/**
	 * @author zhangxiantao
	 * 2016年4月26日 描述：无返回数据
	 * @param msg
	 * @return
	 */
	public static RedisResult<?> buildNoResult(String... msg) {
		return buildResult(RedisConstants.NO_RETURN, buildString(msg), null);
	}
	
	/**
	 * @author zhangxiantao
	 * 2016年4月26日 描述：程序异常，或参数异常
	 * @param msg
	 * @return
	 */
	public static RedisResult<?> buildFailResult(String... msg) {
		return buildResult(RedisConstants.FAILURE, buildString(msg), null);
	}
	
	/**
	 * @author zhangxiantao 2016年4月26日 描述：执行返回封装
	 * @param code
	 * @param msg
	 * @param obj
	 * @return
	 */
	public static RedisResult<?> buildResult(int code, String msg, Object obj) {
		RedisResult<Object> result = new RedisResult<Object>();
		result.setResult(obj);
		result.setCode(code);
		result.setErrMsg(msg);
		return result;
	}

	private static String buildString(String... str) {
		StringBuilder buf = new StringBuilder();
		for (String s : str) {
			buf.append(s);
		}
		return buf.toString();
	}
}
