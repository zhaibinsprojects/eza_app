package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_source;

@Repository
public interface ezs_sourceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_source record);

    int insertSelective(ezs_source record);

    ezs_source selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_source record);

    int updateByPrimaryKey(ezs_source record);
}