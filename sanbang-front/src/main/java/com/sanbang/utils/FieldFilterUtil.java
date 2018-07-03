package com.sanbang.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sanbang.bean.ezs_goods;
/**
 * 集合字段过滤
 * @param <T>
 */
public class FieldFilterUtil<T> {
	/**
	 * 字段过滤 
	 * @param list
	 * @param filterFields
	 * @param clazz
	 * @return
	 */
	public List<T> getFieldFilterList(List<T> list,String filterFields,Class<T> clazz) 
			throws Exception {
		List<T> tempList = new ArrayList<>();
		if(list.size()<=0){
			return tempList;
		}
		if(filterFields==null||filterFields.trim().equals("")){
			return tempList;
		}
		//获取字段数组
		Field[] fields = clazz.getDeclaredFields();
		for (int i=0;i<list.size();i++) {
			//创建对象
			T ob = clazz.newInstance();
			for (Field field : fields) {
				//取消每个属性的安全检查 ,否則无法获取private字段值
				field.setAccessible(true);  
				//存在即保留（确定需要筛选出来的字段）
				if(filterFields.indexOf(field.getName())<0)
					continue;
				//获取字段的属性
				String type = field.getGenericType().toString();
				Class typeTemp = getFieldType(type);
				//获取属性名称
				String name = field.getName();
				String fieldName = name.substring(0, 1).toUpperCase()+name.substring(1,name.length());
				//设置字段值
				Method methodSet = ob.getClass().getMethod("set"+fieldName, typeTemp);
				methodSet.invoke(ob,field.get(list.get(i)));
			}
			tempList.add(ob);
		}
		return tempList;
	}
	
	/**
	 * 字段过滤 
	 * @param oob 需要过滤的对象
	 * @param filterFields
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T getFieldFilter(T oob,String filterFields,Class<T> clazz) 
			throws Exception {
		if(filterFields==null||filterFields.trim().equals("")){
			return (T) new Object();
		}
		//获取字段数组
		Field[] fields = clazz.getDeclaredFields();
		//创建对象
		T ob = clazz.newInstance();
		for (Field field : fields) {
			//取消每个属性的安全检查 ,否則无法获取private字段值
			field.setAccessible(true);  
			//存在即保留（确定需要筛选出来的字段）
			if(filterFields.indexOf(field.getName())<0)
				continue;
			//获取字段的属性
			String type = field.getGenericType().toString();
			Class typeTemp = getFieldType(type);
			//获取属性名称
			String name = field.getName();
			String fieldName = name.substring(0, 1).toUpperCase()+name.substring(1,name.length());
			//设置字段值
			Method methodSet = ob.getClass().getMethod("set"+fieldName, typeTemp);
			methodSet.invoke(ob,field.get(oob));
		}
		return ob;
	}
	/**
	 * 返回字段类型
	 * @param type
	 * @return
	 */
	public static Class getFieldType(String type){
		Class typeTemp = null;
		if(type.equals("class java.lang.String"))
			typeTemp = String.class;
		else if(type.equals("class java.lang.Integer"))
			typeTemp = Integer.class;
		else if(type.equals("class java.lang.Boolean"))
			typeTemp = Boolean.class;
		else if(type.equals("class java.util.Date"))
			typeTemp = java.util.Date.class;
		else if(type.equals("class java.lang.Double"))
			typeTemp = Double.class;
		else if(type.equals("class java.lang.Long"))
			typeTemp = Long.class;
		else
			typeTemp = Object.class;
		return typeTemp;
	}
	
	
	public static void main(String[] args) throws Exception {

		List<ezs_goods> userList = new ArrayList<>();
		ezs_goods good01 = new ezs_goods();
		good01.setName("zhang001");
		good01.setAddess("河南");
		good01.setAddTime(new Date());
		userList.add(good01);
		FieldFilterUtil<ezs_goods> fieldFilterUtil = new FieldFilterUtil<>();
		String filterFields = "name,addess,addtime";
		//String filterFields = "name";
		try {
			//List<ezs_goods> ulist = fieldFilterUtil.getFieldFilterList(userList, filterFields, ezs_goods.class);
			ezs_goods goodTemp = fieldFilterUtil.getFieldFilter(good01, filterFields, ezs_goods.class);
			System.out.println(goodTemp.getName()+" "+goodTemp.getAddess()+" "+goodTemp.getAddTime());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
