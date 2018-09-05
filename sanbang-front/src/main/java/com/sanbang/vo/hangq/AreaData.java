package com.sanbang.vo.hangq;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AreaData {

	
	public  static String area1="1:华东@上海市,福建省,浙江省,安徽省,江苏省,山东省";
	public  static String area2="2:华南@广东省,广西壮族自治区,海南省";
	public  static String area3="3:华北@内蒙古自治区,天津市,山西省,河北省,北京市";
	public  static String area4="4:华中@湖南省,湖北省,河南省,江西省";
	public  static String area5="5:西北@新疆维吾尔自治区,青海省,宁夏回族自治区,甘肃省,陕西省";
	public  static String area6="6:西南@西藏自治区,重庆市,四川省,贵州市,云南省";
	public  static String area7="7:东北@辽宁省,吉林省,黑龙江省,香港";
	
	private static String provincejson="{\r\n" + 
			"'RECORDS':[\r\n" + 
			"{\r\n" + 
			"'id':4521984,\r\n" + 
			"'name':'北京市'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522003,\r\n" + 
			"'name':'天津市'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522022,\r\n" + 
			"'name':'河北省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522217,\r\n" + 
			"'name':'山西省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522359,\r\n" + 
			"'name':'内蒙古自治区'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522482,\r\n" + 
			"'name':'辽宁省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522611,\r\n" + 
			"'name':'吉林省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522689,\r\n" + 
			"'name':'黑龙江省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522847,\r\n" + 
			"'name':'上海市'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4522868,\r\n" + 
			"'name':'江苏省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523000,\r\n" + 
			"'name':'浙江省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523113,\r\n" + 
			"'name':'安徽省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523253,\r\n" + 
			"'name':'福建省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523357,\r\n" + 
			"'name':'江西省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523480,\r\n" + 
			"'name':'山东省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523655,\r\n" + 
			"'name':'河南省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523850,\r\n" + 
			"'name':'湖北省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4523980,\r\n" + 
			"'name':'湖南省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524130,\r\n" + 
			"'name':'广东省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524292,\r\n" + 
			"'name':'广西壮族自治区'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524431,\r\n" + 
			"'name':'海南省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524460,\r\n" + 
			"'name':'重庆市'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524503,\r\n" + 
			"'name':'四川省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524724,\r\n" + 
			"'name':'贵州省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524825,\r\n" + 
			"'name':'云南省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4524979,\r\n" + 
			"'name':'西藏自治区'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4525061,\r\n" + 
			"'name':'陕西省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4525189,\r\n" + 
			"'name':'甘肃省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4525302,\r\n" + 
			"'name':'青海省'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4525355,\r\n" + 
			"'name':'宁夏回族自治区'\r\n" + 
			"},\r\n" + 
			"{\r\n" + 
			"'id':4525388,\r\n" + 
			"'name':'新疆维吾尔自治区'\r\n" + 
			"}\r\n" + 
			"]\r\n" + 
			"}";

	public static String[] getareaName(String areaName) {
		String[] aa=new String[2];
		if(area1.indexOf(areaName)>0) {
			aa[0]=area1.split(":")[0];
			aa[1]=area1.split(":")[1].split("@")[0];
		}
		
		if(area2.indexOf(areaName)>0) {
			aa[0]=area2.split(":")[0];
			aa[1]=area2.split(":")[1].split("@")[0];
		}
		
		if(area3.indexOf(areaName)>0) {
			aa[0]=area3.split(":")[0];
			aa[1]=area3.split(":")[1].split("@")[0];
		}
		if(area4.indexOf(areaName)>0) {
			aa[0]=area4.split(":")[0];
			aa[1]=area4.split(":")[1].split("@")[0];
		}
		if(area5.indexOf(areaName)>0) {
			aa[0]=area5.split(":")[0];
			aa[1]=area5.split(":")[1].split("@")[0];
		}
		if(area1.indexOf(areaName)>0) {
			aa[0]=area6.split(":")[0];
			aa[1]=area6.split(":")[1].split("@")[0];
		}
		
		if(area7.indexOf(areaName)>0) {
			aa[0]=area7.split(":")[0];
			aa[1]=area7.split(":")[1].split("@")[0];
		}
		return aa; 
	}
	
	private  static List<Map<String, Object>> getdaQu() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("1", "华东");
		map.put("2", "华南");
		map.put("3", "华北");
		map.put("4", "华中");
		map.put("5", "西北");
		map.put("6", "西南");
		map.put("7", "东北");
		for (Entry<String, Object> index : map.entrySet()) {
			Map<String, Object> chace = new HashMap<>();
			List<HangqAreaData> alist=new ArrayList<>();
			chace.put("id", index.getKey());
			chace.put("AreaName", index.getValue());
			chace.put("children",alist );
			list.add(chace);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public  static  List<Map<String, Object>> getDaQuArea(List<Map<String, Object>> alist) {
		List<Map<String, Object>> listdaq=getdaQu();
		for (Map<String, Object> map : alist) {
			String name=(String) map.get("areaName");
			map.remove("areaName");
			map.put("AreaName", name);
			String id=getareaName(String.valueOf(map.get("AreaName")))[0];
			if(null!=id) {
				Map<String, Object> map1=listdaq.get(Integer.valueOf(id)-1);
				List<Map<String, Object>> children=(List<Map<String, Object>>) map1.get("children");
				
				children.add(map);
				map1.put("children", children);
			}
		}
	return listdaq;
	}
	
	
	/*@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		List<Map<String, Object>> listdaq=getdaQu();
		List<HangqAreaData> alist=new ArrayList<>();
		for (HangqAreaData hangqAreaData : alist) {
			String id=getareaName(hangqAreaData.getAreaName())[0];
			if(null!=id) {
				Map<String, Object> map=listdaq.get(Integer.valueOf(id));
				List<HangqAreaData> children=(List<HangqAreaData>) map.get("children");
				children.add(hangqAreaData);
			}
		}
	}*/
	
	
	 public static <T> List<T> convertListMap2ListBean(List<Map<String,Object>> listMap, Class T) throws Exception {  
	        List<T> beanList = new ArrayList<T>();
	        for(int i=0, n=listMap.size(); i<n; i++){
	            Map<String,Object> map = listMap.get(i);
	            T bean = convertMap2Bean(map,T);
	            beanList.add(bean);
	        }
	        return beanList;  
	    }
	 
	    public static Map convertBean2Map(Object bean) throws Exception {  
	        Class type = bean.getClass();  
	        Map returnMap = new HashMap();  
	        BeanInfo beanInfo = Introspector.getBeanInfo(type);  
	        PropertyDescriptor[] propertyDescriptors = beanInfo  
	                .getPropertyDescriptors();  
	        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {  
	            PropertyDescriptor descriptor = propertyDescriptors[i];  
	            String propertyName = descriptor.getName();  
	            if (!propertyName.equals("class")) {  
	                Method readMethod = descriptor.getReadMethod();  
	                Object result = readMethod.invoke(bean, new Object[0]);  
	                if (result != null) {  
	                    returnMap.put(propertyName, result);  
	                } else {  
	                    returnMap.put(propertyName, "");  
	                }  
	            }  
	        }  
	        return returnMap;  
	    }
	    
	    public static <T> T convertMap2Bean(Map map, Class T) throws Exception {  
	         if(map==null || map.size()==0){
	             return null;
	        }
	        BeanInfo beanInfo = Introspector.getBeanInfo(T);  
	        T bean = (T)T.newInstance(); 
	        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	        for (int i = 0, n = propertyDescriptors.length; i <n ; i++) {  
	            PropertyDescriptor descriptor = propertyDescriptors[i];  
	            String propertyName = descriptor.getName(); 
	            String upperPropertyName = propertyName.toUpperCase();
	            if (map.containsKey(upperPropertyName)) { 
	                Object value = map.get(upperPropertyName);  
	                //这个方法不会报参数类型不匹配的错误。
	                BeanUtils.copyProperty(bean, propertyName, value);
	//用这个方法对int等类型会报参数类型不匹配错误，需要我们手动判断类型进行转换，比较麻烦。
	//descriptor.getWriteMethod().invoke(bean, value);
	//用这个方法对时间等类型会报参数类型不匹配错误，也需要我们手动判断类型进行转换，比较麻烦。
	//BeanUtils.setProperty(bean, propertyName, value);
	            }  
	        }  
	        return bean;  
	    } 
	    
	    /**
	     * 得到省id
	     * @param id
	     * @return
	     */
	    public static  List<String> getprovinceids(int id) {
	    	 List<String> list=new ArrayList<>();
	    	 if(id>8) {
	    		 list.add(String.valueOf(id));
	    		 return list;
	    	 }
	    	 
	    	JSONObject json=JSONObject.fromObject(provincejson);
	    	Object  o=json.get("RECORDS");
	    	JSONArray array=JSONArray.fromObject(o);
	    	String provinces="";
	    	switch (id) {
		case 1:
			provinces=area1.split("@")[1];
			break;
		case 2:
			provinces=area2.split("@")[1];
			break;
		case 3:
			provinces=area3.split("@")[1];
			break;
		case 4:
			provinces=area4.split("@")[1];
			break;
		case 5:
			provinces=area5.split("@")[1];
			break;
		case 6:
			provinces=area6.split("@")[1];
			break;
		case 7:
			provinces=area7.split("@")[1];
			break;
			default:
				break;
			}
	    	for (Object object : array) {
	    		JSONObject js=JSONObject.fromObject(object);
				for (String p : provinces.split(",")) {
					if(js.get("name").equals(p)) {
						list.add(String.valueOf(js.get("id")));
					}
				}
			}
			return list;
	    }
	    
	    public static void main(String[] args) {
	    	List<String> list=getprovinceids(3);
	    	for (String string : list) {
	    		System.out.println(string);
			}
		}
}
