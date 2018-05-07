package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_audit_log;

@Repository
public interface ezs_goods_audit_logMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods_audit_log record);

    int insertSelective(ezs_goods_audit_log record);

    ezs_goods_audit_log selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods_audit_log record);

    int updateByPrimaryKey(ezs_goods_audit_log record);
}