package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_contact;

@Repository
public interface ezs_contactMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_contact record);

    int insertSelective(ezs_contact record);

    ezs_contact selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_contact record);

    int updateByPrimaryKey(ezs_contact record);
}