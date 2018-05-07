package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_dict;

@Repository
public interface ezs_dictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_dict record);

    int insertSelective(ezs_dict record);

    ezs_dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_dict record);

    int updateByPrimaryKey(ezs_dict record);
}