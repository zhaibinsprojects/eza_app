package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_order_operat;

@Repository
public interface ezs_order_operatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_order_operat record);

    int insertSelective(ezs_order_operat record);

    ezs_order_operat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_order_operat record);

    int updateByPrimaryKey(ezs_order_operat record);
}