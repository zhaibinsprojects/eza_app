package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_main_business;

@Repository
public interface ezs_main_businessMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_main_business record);

    int insertSelective(ezs_main_business record);

    ezs_main_business selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_main_business record);

    int updateByPrimaryKey(ezs_main_business record);
}