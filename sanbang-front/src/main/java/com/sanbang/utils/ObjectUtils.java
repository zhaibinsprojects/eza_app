package com.sanbang.utils;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * object 工具类
 * <ul>
 * <li>1、开发日期：2017年2月10日</li>
 * <li>2、开发时间：10:00</li>
 * <li>3、作 者：Guanfengliang</li>
 * <li>4、类型名称：ObjectUtils</li>
 * <li>5、类型意图：</li>
 * </ul>
 * 
 */
public class ObjectUtils {

    private static final Logger logger =Logger.getLogger(ObjectUtils.class.getName());
    
    /**
     * 读取javaBean所有属性名称
     * @author guanfl
     * @param t
     * @return
     */
    public static <T> List<Object> getAllValue(T t){
    	 
    	//得到实体类对象
    	Class userCla = t.getClass();
    	List<Object> filterFields = new ArrayList<>();
    	Field[] fs = userCla.getDeclaredFields();
    	for(int i = 0;i<fs.length;i++){
    		Field f = fs[i];
    		//设置这么属性是可以访问的
    		f.setAccessible(true);
    		 try {
             	//得到此属性的值
             	Object val = f.getName();
             	if(f.getName().equals("serialVersionUID")){
             		continue;
             	}
             	filterFields.add(val);
 			} catch (Exception e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} 
    	}
    	
    	return filterFields;
    }

    /**
     * 判断实体中属性值是否都为空
     * <ul>
     * <li>1、开发日期：2014年8月13日</li>
     * <li>2、开发时间：下午2:20:41</li>
     * <li>3、作 者：WangJun</li>
     * <li>4、返回类型：boolean</li>
     * <li>5、方法含义：</li>
     * <li>6、方法说明：</li>
     * </ul>
     *
     * @param t 目标实体
     * @param filterFields 需要判断属性名数组
     * @return
     */
    public static <T> boolean valiObjectIsAllNull(T t)
    {
    	//得到实体类对象
    	Class  userCla = (Class)t.getClass();
    	List<Object> filterFields = new ArrayList<>(); 
    	//得到类中的所有属性值集合
    	Field[] fs = userCla.getDeclaredFields();
    	for(int i = 0 ; i < fs.length; i++){  
    		Field f = fs[i];  
            f.setAccessible(true); //设置些属性是可以访问的  
            try {
            	//得到此属性的值
            	Object val = f.get(t);
            	if(f.getName().equals("serialVersionUID")){
            		continue;
            	}
            	filterFields.add(val);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
        boolean flag = true;
        try
        {
            for (int i = 0; i < filterFields.size(); i++)
            {
                if (filterFields.get(i)!=null)
                {
                    flag = false;
                    break;
                }
            }

        }
        catch (Exception e)
        {
            logger.error("判断实体中属性值是否都为空异常" + e.getMessage());
        }
        return flag;
    }

    /**
     * 根据属性名获取属性值
     * */
    public static Object getFieldValueByName(String fieldName, Object o)
    {
        try
        {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        }
        catch (Exception e)
        {
            logger.error("根据属性名获取属性值异常" + e.getMessage());
            return null;
        }
    }

    /**
     * 将实体Stirng类型为null的值替换
     * <ul>
     * <li>1、开发日期：2014年9月28日</li>
     * <li>2、开发时间：上午9:59:39</li>
     * <li>3、作 者：WangJun</li>
     * <li>4、返回类型：void</li>
     * <li>5、方法含义：</li>
     * <li>6、方法说明：</li>
     * </ul>
     *
     * @param t 需要修改对象
     * @param beans 通过 Object.class.getDeclaredFields()获取需要替换的属性数组 例如： Field[]
     *            beans = ApplicationVo.class.getDeclaredFields();
     * @param toString 期望替换值
     */
    public static <T> void nullConventsEmpty(T t, Field[] beans, String toString)
    {
        for (Field field : beans)
        {
            if (("String").equals(field.getType().getSimpleName()) && (ObjectUtils.getFieldValueByName(field.getName(), t) == null))
            {
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String setter = "set" + firstLetter + field.getName().substring(1);
                try
                {
                    @SuppressWarnings("rawtypes")
                    Class[] parameterTypes = new Class[1];// 这里你要调用的方法只有一个参数

                    parameterTypes[0] = String.class;// 这个参数的类型是String型的

                    Method method = t.getClass().getMethod(setter, parameterTypes);
                    try
                    {
                        method.invoke(t, new Object[] { toString });
                    }
                    catch (IllegalAccessException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    }
                    catch (IllegalArgumentException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    }
                    catch (InvocationTargetException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    }

                }
                catch (NoSuchMethodException e)
                {
                    // TODO Auto-generated catc
                    logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    e.printStackTrace();
                }
                catch (SecurityException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                }

            }
        }
    }

    /**
     * 将实体Stirng类型为null的值替换
     * <ul>
     * <li>1、开发日期：2014年9月28日</li>
     * <li>2、开发时间：上午9:59:39</li>
     * <li>3、作 者：WangJun</li>
     * <li>4、返回类型：void</li>
     * <li>5、方法含义：</li>
     * <li>6、方法说明：</li>
     * </ul>
     *
     * @param t 需要修改对象
     * @param beans 通过 Object.class.getDeclaredFields()获取需要替换的属性数组 例如： Field[]
     *            beans = ApplicationVo.class.getDeclaredFields();
     * @param toString 期望替换值
     */
    public static <T> void emptyConventsNull(T t, Field[] beans)
    {
        for (Field field : beans)
        {
            if (("String").equals(field.getType().getSimpleName()) && ("").equals(ObjectUtils.getFieldValueByName(field.getName(), t)))
            {
                String firstLetter = field.getName().substring(0, 1).toUpperCase();
                String setter = "set" + firstLetter + field.getName().substring(1);
                try
                {
                    @SuppressWarnings("rawtypes")
                    Class[] parameterTypes = new Class[1];// 这里你要调用的方法只有一个参数

                    parameterTypes[0] = String.class;// 这个参数的类型是String型的

                    Method method = t.getClass().getMethod(setter, parameterTypes);
                    try
                    {
                        method.invoke(t, new Object[] { null });
                    }
                    catch (IllegalAccessException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    }
                    catch (IllegalArgumentException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    }
                    catch (InvocationTargetException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    }

                }
                catch (NoSuchMethodException e)
                {
                    // TODO Auto-generated catc
                    logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                    e.printStackTrace();
                }
                catch (SecurityException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    logger.error("将实体Stirng类型为null的值替换异常:" + e.getMessage(), e);
                }

            }
        }
    }
}
