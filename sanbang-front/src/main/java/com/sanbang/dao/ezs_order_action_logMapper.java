package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_order_action_log;

@Repository
public interface ezs_order_action_logMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_order_action_log record);

    int insertSelective(ezs_order_action_log record);

    ezs_order_action_log selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_order_action_log record);

    int updateByPrimaryKey(ezs_order_action_log record);
}