package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_specialsubject;

@Repository
public interface ezs_specialsubjectMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_specialsubject record);

    int insertSelective(ezs_specialsubject record);

    ezs_specialsubject selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_specialsubject record);

    int updateByPrimaryKey(ezs_specialsubject record);
}