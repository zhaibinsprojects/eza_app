package com.sanbang.utils;

import java.lang.reflect.Field;


public class SoundReflect {

	public static <T> void toModifyValue(Class<T> clazz,Object o){
		try {
			 Field[] field = clazz.getDeclaredFields();
			 for (int i = 0; i < field.length; i++) {
				 field[i].setAccessible(true);//修改访问权限
		            Class<?> type = field[i].getType();
				    Object oo=field[i].get(o);
				    if(oo==null){
				    	if(type.getSimpleName().equals("String")){
				    		field[i].set(o, "");
				    	}else if(type.getSimpleName().equals("Integer")){
				    		field[i].set(o, 0);
				    	}else if(type.getSimpleName().equals("Long")){
				    		field[i].set(o, 0l);
				    	}else if(type.getSimpleName().equals("Double")){
				    		field[i].set(o, 0.0);
				    	}else if(type.getSimpleName().equals("Float")){
				    		field[i].set(o, 0f);
				    	}else if(type.getSimpleName().equals("Short")){
				    		field[i].set(o, (short)0);
				    	}else if(type.getSimpleName().equals("Boolean")){
				    		field[i].set(o, false);
				    	}else if(type.getSimpleName().equals("Character")){
				    		field[i].set(o, ' ');
				    	}else if(type.getSimpleName().equals(clazz.getSimpleName())){
				    		field[i].set(o, clazz.newInstance());
				    		System.out.println("对象为空了");
				    		
				    	}
				    	System.out.println(field[i].get(o));
				    }
		        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
