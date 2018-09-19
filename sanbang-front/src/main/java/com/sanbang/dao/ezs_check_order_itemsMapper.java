package com.sanbang.dao;

import com.sanbang.bean.ezs_check_order_items;

public interface ezs_check_order_itemsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_check_order_items record);

    int insertSelective(ezs_check_order_items record);

    ezs_check_order_items selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_check_order_items record);

    int updateByPrimaryKey(ezs_check_order_items record);
}