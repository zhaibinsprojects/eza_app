package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_role_group;

@Repository
public interface ezs_role_groupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_role_group record);

    int insertSelective(ezs_role_group record);

    ezs_role_group selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_role_group record);

    int updateByPrimaryKey(ezs_role_group record);
}