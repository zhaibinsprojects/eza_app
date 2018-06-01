package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_class;
import com.sanbang.vo.GoodsClass;


@Repository(value="ezs_cataMapper")
public interface ezs_cataMapper {
	/**
	 * 查询一级分类
	 */
	List<ezs_goods_class> getFirstList();
	/**
	 * 查询二级、三级分类
	 */
	List<GoodsClass> getChildList();
	
}