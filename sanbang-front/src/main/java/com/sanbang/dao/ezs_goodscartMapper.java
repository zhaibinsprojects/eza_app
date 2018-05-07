package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goodscart;

@Repository
public interface ezs_goodscartMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goodscart record);

    int insertSelective(ezs_goodscart record);

    ezs_goodscart selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goodscart record);

    int updateByPrimaryKey(ezs_goodscart record);
}