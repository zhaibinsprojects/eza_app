package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_clean_log;

@Repository
public interface ezs_clean_logMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_clean_log record);

    int insertSelective(ezs_clean_log record);

    ezs_clean_log selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_clean_log record);

    int updateByPrimaryKey(ezs_clean_log record);
}