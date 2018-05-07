package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_set_return_order_operat;

@Repository
public interface ezs_set_return_order_operatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_set_return_order_operat record);

    int insertSelective(ezs_set_return_order_operat record);

    ezs_set_return_order_operat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_set_return_order_operat record);

    int updateByPrimaryKey(ezs_set_return_order_operat record);
}