package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_store_stat;

@Repository
public interface ezs_store_statMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_store_stat record);

    int insertSelective(ezs_store_stat record);

    ezs_store_stat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_store_stat record);

    int updateByPrimaryKey(ezs_store_stat record);
}