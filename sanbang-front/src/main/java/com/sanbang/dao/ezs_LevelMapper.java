package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_level;

@Repository
public interface ezs_LevelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_level record);

    int insertSelective(ezs_level record);

    ezs_level selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_level record);

    int updateByPrimaryKey(ezs_level record);
}