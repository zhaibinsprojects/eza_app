package com.sanbang.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * 对List<T>泛型集合T类型进行内部字段过滤
 * @author zhaibin
 * @param <E>
 */
public class FieldFilterUtil<T>{
	
	private static Logger log = Logger.getLogger(FieldFilterUtil.class);
	
	@SuppressWarnings({ "unused", "unchecked" })
	public List<T> getFieldFilterList(List<T> list,String filterFields,Class<?> clazz) throws InstantiationException,IllegalAccessException {
		List<T> tempList = new ArrayList<>();
		if(list.size()<=0){
			System.out.println(" ");
			return null;
		}
		if(filterFields==null||filterFields.trim().equals("")){
			log.info("过滤地段为NUll");
			return null;
		}
		//获取字段数组
		Field[] fields = clazz.getDeclaredFields();
		//取消每个属性的安全检查 ,否則无法获取private字段值
        for(Field f:fields){  
            f.setAccessible(true);  
        }  
		for (int i=0;i<list.size();i++) {
			//T object = (T) clazz.getClass().getDeclaredFields()
			//Object ob = new Object();
			//fields[i].set(obj, value);
			
			
			for (Field field : fields) {
				System.out.println(field.getName()+" "+field.get(list.get(i)));
			}
			//tempList.add();
		}
		return tempList;
	}



}
