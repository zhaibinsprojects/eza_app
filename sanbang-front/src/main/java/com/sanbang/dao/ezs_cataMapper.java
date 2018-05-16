package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.sanbang.bean.ezs_goods_class;


@Repository(value="ezs_cataMapper")
public interface ezs_cataMapper {
	/**
	 * 查询一级分类
	 */
	List<ezs_goods_class> getOnelevelList();
	/**
	 * 查询二级分类
	 * @param parentId
	 * @return
	 */
	List<ezs_goods_class> getTwolevelList(long parentId);
	/**
	 * 查询三级分类
	 * @param parentId
	 * @return
	 */
	List<ezs_goods_class> getThreelevelList(long parentId);
	/**
	 * 自营，地区、类别筛选
	 * @param area 地区
	 * @param type 类别
	 * @return
	 */
	List<ezs_goods_class> listByAreaAndType(String area,String type);
	/**
	 * 其他筛选条件
	 * @param terms	字符串数组
	 * @return
	 */
	List<ezs_goods_class> listByOthers(String[] terms);
	
	
}