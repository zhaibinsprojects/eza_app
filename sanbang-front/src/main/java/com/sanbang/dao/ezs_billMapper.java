package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_bill;

@Repository
public interface ezs_billMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_bill record);

    int insertSelective(ezs_bill record);

    ezs_bill selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_bill record);

    int updateByPrimaryKey(ezs_bill record);
}