package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_priceadjust;

@Repository
public interface ezs_priceadjustMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_priceadjust record);

    int insertSelective(ezs_priceadjust record);

    ezs_priceadjust selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_priceadjust record);

    int updateByPrimaryKey(ezs_priceadjust record);
}