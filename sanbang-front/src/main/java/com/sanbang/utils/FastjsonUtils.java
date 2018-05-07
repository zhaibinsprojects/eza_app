package com.sanbang.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * <p>fastjson工具类</p>
 * @author zhangxiantao 2016年3月12日
 * @version 1.0
 */
public class FastjsonUtils {

	/**
	 * 检测字符串是否为json格式
	 * @author zhangxiantao 2016年3月12日
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static boolean checkJsonStr(String jsonStr){
		boolean flag = false;
		try {
			if (StringUtils.hasText(jsonStr)) {
				JSONObject.parse(jsonStr);
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * <p>实体对象转换为JSON字符串</p>
	 * @author zhangxiantao 2016年3月12日
	 * @param object
	 * @return
	 * @throws Exception
	 */
	/**
	 * <p>实体对象转换为JSON字符串</p>
	 * @author zhangxiantao 2016年3月12日
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public static String beanToJson(Object object){
		return JSON.toJSONString(object, true);
	}
	
	/**
	 * <p>Map转换为JSON字符串</p>
	 * @author zhangxiantao 2016年3月12日
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String mapToJson(Map<String, Object> map) throws Exception{
		try {
			return JSON.toJSONString(map, true);
		} catch (Exception e) {
			throw new Exception("map转换JSON时出错", e);
		}
	}
	
	/**
	 * <p>List转换为JSON字符串</p>
	 * @author zhangxiantao 2016年3月12日
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String listToJson(List<?> list) throws Exception{
		try {
			return JSON.toJSONString(list, true);
		} catch (Exception e) {
			throw new Exception("list转换JSON时出错", e);
		}
	}
	
	/**
	 * <p>array字符串转换为JSON字符串</p>
	 * @author zhangxiantao 2016年3月12日
	 * @param arrayStr
	 * @return
	 * @throws Exception
	 */
	public static String arrayToJson(Object[] array) throws Exception{
		try {
			return JSON.toJSONString(array, true);
		} catch (Exception e) {
			throw new Exception("array转换JSON时出错", e);
		}
	}
	
	/**
	 * <p>JSON转换为Map</p>
	 * @author zhangxiantao 2016年3月12日
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> jsonToMap(String jsonStr) throws Exception{
		Map<String, Object> returnMap = null;
		try {
			if (checkJsonStr(jsonStr)) {
				returnMap = new HashMap<String, Object>();
				JSONObject jsonObject = JSON.parseObject(jsonStr);
				for (Entry<String, Object> entry : jsonObject.entrySet()) {
					returnMap.put(entry.getKey(), entry.getValue());
				}
			}
		} catch (Exception e) {
			throw new Exception("JSON转换MAP时出错", e);
		}
		return returnMap;
	}
	
	/**
	 * <p>JSON转换为List/p>
	 * <p>注意事项：</p>
	 * <ul>
	 * 	<li>list泛型对应实体中含有list属性也可转换</li>
	 * </ul>
	 * @author zhangxiantao 2016年3月12日
	 * @param className
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static List<?> jsonToList(Class<?> className, String jsonStr) throws Exception{
		List<?> returnList = null;
		try {
			if (null != className && checkJsonStr(jsonStr)) {
				returnList = JSON.parseArray(jsonStr, className);
			}
		} catch (Exception e) {
			throw new Exception("JSON转换LIST<?>时出错", e);
		}
		return returnList;
	}
	
	/**
	 * <p>JSON转换为实体</p>
	 * <p>注意事项：</p>
	 * <ul>
	 * 	<li>实体中含有list属性也可转换</li>
	 * </ul>
	 * @author zhangxiantao 2016年3月12日
	 * @param className
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Object jsonToBean(Class<?> className, String jsonStr) throws Exception{
		Object returnObject = null;
		try {
			if (null != className && checkJsonStr(jsonStr)) {
				returnObject = JSONObject.toJavaObject(JSONObject.parseObject(jsonStr), className);
			}
		} catch (Exception e) {
			throw new Exception("JSON转换为实体时出错", e);
		}
		return returnObject;
	}
	
	/**
	 * <p>JSON转换为数组</p>
	 * @author zhangxiantao 2016年3月12日
	 * @param className
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static Object[] jsonToArray(Class<?> className, String jsonStr) throws Exception{
		Object[] returnArray = null;
		try {
			if (null != className && checkJsonStr(jsonStr)) {
				returnArray = JSON.parseArray(jsonStr).toArray();
			}
		} catch (Exception e) {
			throw new Exception("JSON转换为数组时出错", e);
		}
		return returnArray;
	}
}
