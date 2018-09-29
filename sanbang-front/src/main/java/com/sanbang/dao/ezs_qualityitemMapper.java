package com.sanbang.dao;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_qualityitem;

public interface ezs_qualityitemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_qualityitem record);

    int insertSelective(ezs_qualityitem record);

    ezs_qualityitem selectByPrimaryKey(@Param("id")Long id);

    int updateByPrimaryKeySelective(ezs_qualityitem record);

    int updateByPrimaryKey(ezs_qualityitem record);
}