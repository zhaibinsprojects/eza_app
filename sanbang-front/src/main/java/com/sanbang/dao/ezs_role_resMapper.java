package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_role_res;

@Repository
public interface ezs_role_resMapper {
    int insert(ezs_role_res record);

    int insertSelective(ezs_role_res record);
}