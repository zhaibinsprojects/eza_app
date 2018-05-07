package com.sanbang.utils;

import java.lang.reflect.Method;
import java.util.Map;

public class CommonUtils {

	/**
	 * <p>
	 * 检测实体属性必填字段
	 * </p>
	 * 
	 * @param jsonStr
	 * @param className
	 * @param notNullStr
	 *            必填字段 逗号分隔 ep："aa,bb,..."代表aa bb 为必须有值的属性
	 * @param model
	 * @return Object jsonStr = "" or = null 返回 null
	 * @author zhangxiantao 2016年3月12日
	 */
	public static Object checkBean(String jsonStr, Class<?> className, String notNullStr, Map<String, Object> model)
			throws Exception {
		// 实例化类 jackson
		// ObjectMapper objectMapper = new ObjectMapper();
		// objectMapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// Object object = null;
		// object = objectMapper.readValue(jsonStr, className);
		Object object = FastjsonUtils.jsonToBean(className, jsonStr);
		// 判断实体不为空
		if (null != object && null != notNullStr && !"".equals(notNullStr)) {
			// 必填属性
			String[] requireds = notNullStr.split(",");
			for (String required : requireds) {
				// 拿到必填属性的get方法
				Method metho = (Method) object.getClass().getMethod("get" + getMethodName(required));
				if (null == metho.invoke(object)) {
					// model.put("code", ReturnDatas.PARAM_IS_NULL);
					// model.put("msg", required +
					// ReturnDatas.PARAM_IS_NULL_MSG);
					// throw new Exception(required +
					// ReturnDatas.PARAM_IS_NULL_MSG);
				}
				String val = metho.invoke(object).toString();
				if (null == val || "".equals(val)) {
					// model.put("code", ReturnDatas.PARAM_IS_NULL);
					// model.put("msg", required +
					// ReturnDatas.PARAM_IS_NULL_MSG);
					// throw new Exception(required +
					// ReturnDatas.PARAM_IS_NULL_MSG);
				}
			}
			return object;
		} else {
			if (model.isEmpty()) {
				// model.put("code", ReturnDatas.PARAM_IS_NULL);
				// model.put("msg", "传入参数jsonStr" +
				// ReturnDatas.PARAM_IS_NULL_MSG);
				throw new Exception(model.get("msg").toString());
			}
		}
		return object;
	}

	/**
	 * 把一个字符串的第一个字母大写、效率是最高的、
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
}
