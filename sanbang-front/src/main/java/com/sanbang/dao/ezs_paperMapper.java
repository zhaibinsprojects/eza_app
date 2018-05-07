package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_paper;

@Repository
public interface ezs_paperMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_paper record);

    int insertSelective(ezs_paper record);

    ezs_paper selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_paper record);

    int updateByPrimaryKey(ezs_paper record);
}