package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_class;


@Repository(value="ezs_cataMapper")
public interface ezs_cataMapper {
	/**
	 * 查询品类一级分类
	 */
	List<ezs_goods_class> getListForClass();
	
}