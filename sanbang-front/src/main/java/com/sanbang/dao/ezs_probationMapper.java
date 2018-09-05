package com.sanbang.dao;

import com.sanbang.bean.ezs_probation;

public interface ezs_probationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_probation record);

    int insertSelective(ezs_probation record);

    ezs_probation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_probation record);

    int updateByPrimaryKey(ezs_probation record);
    
    ezs_probation selectProbationByUserId(Long userid);
}