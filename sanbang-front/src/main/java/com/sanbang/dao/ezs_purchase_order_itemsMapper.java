package com.sanbang.dao;

import com.sanbang.bean.ezs_purchase_order_items;

public interface ezs_purchase_order_itemsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_purchase_order_items record);

    int insertSelective(ezs_purchase_order_items record);

    ezs_purchase_order_items selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_purchase_order_items record);

    int updateByPrimaryKeyWithBLOBs(ezs_purchase_order_items record);

    int updateByPrimaryKey(ezs_purchase_order_items record);
}