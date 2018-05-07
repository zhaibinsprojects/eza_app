package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_album;

@Repository
public interface ezs_albumMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_album record);

    int insertSelective(ezs_album record);

    ezs_album selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_album record);

    int updateByPrimaryKeyWithBLOBs(ezs_album record);

    int updateByPrimaryKey(ezs_album record);
}