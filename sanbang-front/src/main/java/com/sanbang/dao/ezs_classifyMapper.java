package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_classify;

@Repository
public interface ezs_classifyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_classify record);

    int insertSelective(ezs_classify record);

    ezs_classify selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_classify record);

    int updateByPrimaryKey(ezs_classify record);
}