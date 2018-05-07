package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_userconfig;

@Repository
public interface ezs_userconfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_userconfig record);

    int insertSelective(ezs_userconfig record);

    ezs_userconfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_userconfig record);

    int updateByPrimaryKey(ezs_userconfig record);
}