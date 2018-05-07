package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_communicate;

@Repository
public interface ezs_communicateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_communicate record);

    int insertSelective(ezs_communicate record);

    ezs_communicate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_communicate record);

    int updateByPrimaryKey(ezs_communicate record);
}