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
	
	
}