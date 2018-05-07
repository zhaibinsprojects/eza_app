package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_documentshare;

@Repository
public interface ezs_documentshareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_documentshare record);

    int insertSelective(ezs_documentshare record);

    ezs_documentshare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_documentshare record);

    int updateByPrimaryKey(ezs_documentshare record);
}