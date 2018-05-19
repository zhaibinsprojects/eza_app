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
	
	
}
