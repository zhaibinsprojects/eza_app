package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_syslog;

@Repository
public interface ezs_syslogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_syslog record);

    int insertSelective(ezs_syslog record);

    ezs_syslog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_syslog record);

    int updateByPrimaryKeyWithBLOBs(ezs_syslog record);

    int updateByPrimaryKey(ezs_syslog record);
}