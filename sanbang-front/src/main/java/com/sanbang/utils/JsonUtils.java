package com.sanbang.utils;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 将Object转化为Json
 * @author zxt
 */
public class JsonUtils {

	//日志
	static Logger log = Logger.getLogger(JsonUtils.class.getName());
	
	/**
	 * 将obj转换为json格式的字符串
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String jsonOut(Object obj){
		String jsonString = "";
		if(obj instanceof String){
			jsonString = (String)obj;
		}else{
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				jsonString = objectMapper.writeValueAsString(obj);
			} catch (Exception e) {
				log.error("----------静态化中,对象转换为json失败");
				log.error(e.toString());
			} 
		}
		return jsonString;
	}

}
