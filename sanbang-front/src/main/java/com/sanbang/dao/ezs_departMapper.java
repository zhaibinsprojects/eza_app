package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_depart;

@Repository
public interface ezs_departMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_depart record);

    int insertSelective(ezs_depart record);

    ezs_depart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_depart record);

    int updateByPrimaryKey(ezs_depart record);
}