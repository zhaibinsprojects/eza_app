package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_exchange;

@Repository
public interface ezs_exchangeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_exchange record);

    int insertSelective(ezs_exchange record);

    ezs_exchange selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_exchange record);

    int updateByPrimaryKey(ezs_exchange record);
}