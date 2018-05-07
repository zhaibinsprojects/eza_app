package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_store;

@Repository
public interface ezs_storeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_store record);

    int insertSelective(ezs_store record);

    ezs_store selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_store record);

    int updateByPrimaryKeyWithBLOBs(ezs_store record);

    int updateByPrimaryKey(ezs_store record);
}