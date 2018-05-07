package com.sanbang.vo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;

/**
 * 
 * 作为项目的字典
 * @author zhangxiantao
 *  
 * 2016年6月28日
 */
public class Dictionary {
	
	//日志
	private static Logger log = Logger.getLogger(Dictionary.class.getName());

	// 易付宝响应字典
	private Map<String,Object> eppsResponseDictionary = new HashMap<String,Object>();

	public Map<String, Object> getEppsResponseDictionary() {
		return eppsResponseDictionary;
	}

	public void setEppsResponseDictionary(Map<String, Object> eppsResponseDictionary) {
		this.eppsResponseDictionary = eppsResponseDictionary;
	}

	public Dictionary(){
	}
	
	public static String getProperties(String key){
		
		// 读取配置文件config.properties
		String path = ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
		InputStream resourceAsStream = null;
		try {
			resourceAsStream = new FileInputStream(path+"WEB-INF/classes/config.properties");
		} catch (FileNotFoundException e1) {
			log.error("配置文件没找到");
			log.error(e1.toString());
		}
		Properties properties = new Properties();
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			log.error("读取配置文件失败");
			log.error(e.toString());
		}
		
		if(properties.containsKey(key)){
			return properties.getProperty(key);
		}
		
		return null;
	}
}
