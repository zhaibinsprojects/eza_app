package com.sanbang.userpro.service;

/**
 * 环信业务相关
 * 
 *  
 * 2017年7月20日
 */
public interface HunXinService{

	void queryHuanxinToken();

	/**
	 * 异步处理
	 * @param username
	 * @param password
	 */
	net.sf.json.JSONObject regHuanxinSingle(String username, String password,Long userid);

	/**
	 * 同步处理
	 * @param username
	 * @param password
	 * @return 
	 */
	net.sf.json.JSONObject regHuanxinSinglesyn(String username, String password);

	void handlMemberData();
}
