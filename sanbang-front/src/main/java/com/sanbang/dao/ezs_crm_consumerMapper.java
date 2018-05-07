package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_crm_consumer;

@Repository
public interface ezs_crm_consumerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_crm_consumer record);

    int insertSelective(ezs_crm_consumer record);

    ezs_crm_consumer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_crm_consumer record);

    int updateByPrimaryKey(ezs_crm_consumer record);
}