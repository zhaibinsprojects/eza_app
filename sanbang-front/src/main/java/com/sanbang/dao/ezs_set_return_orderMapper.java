package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_set_return_order;

@Repository
public interface ezs_set_return_orderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_set_return_order record);

    int insertSelective(ezs_set_return_order record);

    ezs_set_return_order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_set_return_order record);

    int updateByPrimaryKey(ezs_set_return_order record);
}