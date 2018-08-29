package com.sanbang.dao;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_memberorder;

public interface ezs_memberorderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_memberorder record);

    int insertSelective(ezs_memberorder record);

    ezs_memberorder selectByPrimaryKey(@Param("id")Long id);

    int updateByPrimaryKeySelective(ezs_memberorder record);

    int updateByPrimaryKey(ezs_memberorder record);
}