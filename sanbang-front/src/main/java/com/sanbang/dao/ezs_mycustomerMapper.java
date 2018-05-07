package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_mycustomer;

@Repository
public interface ezs_mycustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_mycustomer record);

    int insertSelective(ezs_mycustomer record);

    ezs_mycustomer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_mycustomer record);

    int updateByPrimaryKey(ezs_mycustomer record);
}