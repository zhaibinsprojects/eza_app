package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_dvaluate;

@Repository
public interface ezs_dvaluateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_dvaluate record);

    int insertSelective(ezs_dvaluate record);

    ezs_dvaluate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_dvaluate record);

    int updateByPrimaryKeyWithBLOBs(ezs_dvaluate record);

    int updateByPrimaryKey(ezs_dvaluate record);
}