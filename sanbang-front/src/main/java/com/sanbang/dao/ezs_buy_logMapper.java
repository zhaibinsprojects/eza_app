package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_buy_log;

@Repository
public interface ezs_buy_logMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_buy_log record);

    int insertSelective(ezs_buy_log record);

    ezs_buy_log selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_buy_log record);

    int updateByPrimaryKey(ezs_buy_log record);
}