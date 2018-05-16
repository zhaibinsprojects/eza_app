package com.sanbang.cata.service;

import java.util.List;

/**
 * 品类相关业务处理
 * @author hanlongfei
 * 2018年05月11日
 */
public interface CataService {
	/**
	 * 查询一级分类
	 */
	public List getOnelevelList();
	/**
	 * @param parentId
	 */
	public List getTwolevelList(long parentId);
	/**
	 * 三级
	 * @param parentId	父id
	 */
	public List getThreelevelList(long parentId);
	/**
	 * 自营，地区、品类筛选
	 * @param area
	 * @param type
	 */
	public List listByAreaAndType(String area,String type);
	/**
	 * 其他筛选
	 * @param terms	字符串数组
	 */
	public List listByOthers(String[] terms);
	
	
	
	
	
}
