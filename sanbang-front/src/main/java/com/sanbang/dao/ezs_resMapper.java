package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_res;

@Repository
public interface ezs_resMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_res record);

    int insertSelective(ezs_res record);

    ezs_res selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_res record);

    int updateByPrimaryKey(ezs_res record);
}