package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_customerlog;

@Repository("ezs_customerLogMapper")
public interface ezs_customerLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customerlog record);

    int insertSelective(ezs_customerlog record);

    ezs_customerlog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customerlog record);

    int updateByPrimaryKey(ezs_customerlog record);
}