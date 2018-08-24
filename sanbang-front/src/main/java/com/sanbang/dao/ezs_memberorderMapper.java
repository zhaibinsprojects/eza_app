package com.sanbang.dao;

import com.sanbang.bean.ezs_memberorder;

public interface ezs_memberorderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_memberorder record);

    int insertSelective(ezs_memberorder record);

    ezs_memberorder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_memberorder record);

    int updateByPrimaryKey(ezs_memberorder record);
}