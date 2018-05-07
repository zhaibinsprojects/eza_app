package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_comment;

@Repository
public interface ezs_commentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_comment record);

    int insertSelective(ezs_comment record);

    ezs_comment selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_comment record);

    int updateByPrimaryKey(ezs_comment record);
}