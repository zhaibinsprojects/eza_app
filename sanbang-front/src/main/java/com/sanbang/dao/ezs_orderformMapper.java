package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_orderform;

@Repository
public interface ezs_orderformMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_orderform record);

    int insertSelective(ezs_orderform record);

    ezs_orderform selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_orderform record);

    int updateByPrimaryKeyWithBLOBs(ezs_orderform record);

    int updateByPrimaryKey(ezs_orderform record);
}