package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_address;

@Repository
public interface ezs_addressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_address record);

    int insertSelective(ezs_address record);

    ezs_address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_address record);

    int updateByPrimaryKey(ezs_address record);
}