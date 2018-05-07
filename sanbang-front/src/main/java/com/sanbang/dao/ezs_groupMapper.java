package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_group;

@Repository
public interface ezs_groupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_group record);

    int insertSelective(ezs_group record);

    ezs_group selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_group record);

    int updateByPrimaryKey(ezs_group record);
}