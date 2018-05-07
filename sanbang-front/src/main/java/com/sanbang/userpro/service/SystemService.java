package com.sanbang.userpro.service;

import java.util.Map;



public interface SystemService {

	/**
	 * 登录处理
	 * @param userName
	 * @param password
	 * @return
	 */
	Map<String, Object> login(String userName, String password);

	/**
	 * 修改密码
	 * @param userName
	 * @param password
	 * @return
	 */
	int modifyPassword(String userName, String password);

}
