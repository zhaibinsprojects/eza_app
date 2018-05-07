package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_logistics;

@Repository
public interface ezs_logisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_logistics record);

    int insertSelective(ezs_logistics record);

    ezs_logistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_logistics record);

    int updateByPrimaryKey(ezs_logistics record);
}