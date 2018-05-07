package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_customer_audit;

@Repository
public interface ezs_customer_auditMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customer_audit record);

    int insertSelective(ezs_customer_audit record);

    ezs_customer_audit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customer_audit record);

    int updateByPrimaryKey(ezs_customer_audit record);
}