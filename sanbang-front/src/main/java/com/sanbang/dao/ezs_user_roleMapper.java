package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_user_role;

@Repository
public interface ezs_user_roleMapper {
    int deleteByPrimaryKey(Long user_id);

    int insert(ezs_user_role record);

    int insertSelective(ezs_user_role record);

    ezs_user_role selectByPrimaryKey(Long user_id);

    int updateByPrimaryKeySelective(ezs_user_role record);

    int updateByPrimaryKey(ezs_user_role record);
}