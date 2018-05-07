package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_readjust;

@Repository
public interface ezs_readjustMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_readjust record);

    int insertSelective(ezs_readjust record);

    ezs_readjust selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_readjust record);

    int updateByPrimaryKey(ezs_readjust record);
}