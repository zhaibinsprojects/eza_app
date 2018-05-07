package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_multimedia;

@Repository
public interface ezs_multimediaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_multimedia record);

    int insertSelective(ezs_multimedia record);

    ezs_multimedia selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_multimedia record);

    int updateByPrimaryKey(ezs_multimedia record);
}