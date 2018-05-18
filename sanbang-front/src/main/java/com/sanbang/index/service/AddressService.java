package com.sanbang.index.service;

import java.util.Map;

public interface AddressService {
	/**
	 * 查询地址信息
	 */
	public Map<String, Object> getHotAddress();
	/**
	 * 查询省份信息
	 * @return
	 */
	public Map<String, Object> getProvince();
	/**
	 * 根据父级别地址节点查询子节点
	 * @param area
	 * @return
	 */
	public Map<String, Object> getChildByParents(Long aid);
	
	public Map<String, Object> getParentByChild(Long pId);
}
