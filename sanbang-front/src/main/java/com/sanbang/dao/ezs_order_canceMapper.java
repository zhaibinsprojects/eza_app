package com.sanbang.dao;

import com.sanbang.bean.ezs_order_cance;

public interface ezs_order_canceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_order_cance record);

    int insertSelective(ezs_order_cance record);

    ezs_order_cance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_order_cance record);

    int updateByPrimaryKey(ezs_order_cance record);
}