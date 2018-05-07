package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_role;

@Repository
public interface ezs_roleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_role record);

    int insertSelective(ezs_role record);

    ezs_role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_role record);

    int updateByPrimaryKey(ezs_role record);
}