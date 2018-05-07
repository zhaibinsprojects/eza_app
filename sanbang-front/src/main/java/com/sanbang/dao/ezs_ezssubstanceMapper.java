package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_ezssubstance;

@Repository
public interface ezs_ezssubstanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_ezssubstance record);

    int insertSelective(ezs_ezssubstance record);

    ezs_ezssubstance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_ezssubstance record);

    int updateByPrimaryKey(ezs_ezssubstance record);
}