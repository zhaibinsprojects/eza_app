package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_pact;

@Repository
public interface ezs_pactMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_pact record);

    int insertSelective(ezs_pact record);

    ezs_pact selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_pact record);

    int updateByPrimaryKey(ezs_pact record);
}