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
	/**
	 * 获取所有子节点ID包含一、二、三级
	 * @param aid
	 * @return
	 */
	public Map<String, Object> getAllChildID(Long aid);
}
