package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_activity_template;

@Repository
public interface ezs_activity_templateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_activity_template record);

    int insertSelective(ezs_activity_template record);

    ezs_activity_template selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_activity_template record);

    int updateByPrimaryKey(ezs_activity_template record);
}