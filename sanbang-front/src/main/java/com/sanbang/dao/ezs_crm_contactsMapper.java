package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_crm_contacts;

@Repository
public interface ezs_crm_contactsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_crm_contacts record);

    int insertSelective(ezs_crm_contacts record);

    ezs_crm_contacts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_crm_contacts record);

    int updateByPrimaryKey(ezs_crm_contacts record);
}