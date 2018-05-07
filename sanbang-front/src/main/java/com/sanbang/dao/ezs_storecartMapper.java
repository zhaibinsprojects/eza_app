package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_storecart;

@Repository
public interface ezs_storecartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_storecart record);

    int insertSelective(ezs_storecart record);

    ezs_storecart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_storecart record);

    int updateByPrimaryKey(ezs_storecart record);
}