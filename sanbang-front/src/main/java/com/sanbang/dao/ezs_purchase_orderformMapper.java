package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_purchase_orderform;

@Repository
public interface ezs_purchase_orderformMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_purchase_orderform record);

    int insertSelective(ezs_purchase_orderform record);

    ezs_purchase_orderform selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_purchase_orderform record);

    int updateByPrimaryKeyWithBLOBs(ezs_purchase_orderform record);

    int updateByPrimaryKey(ezs_purchase_orderform record);
}