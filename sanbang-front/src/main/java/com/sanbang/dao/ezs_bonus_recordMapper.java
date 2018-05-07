package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_bonus_record;

@Repository
public interface ezs_bonus_recordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_bonus_record record);

    int insertSelective(ezs_bonus_record record);

    ezs_bonus_record selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_bonus_record record);

    int updateByPrimaryKey(ezs_bonus_record record);
}