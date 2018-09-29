package com.sanbang.dao;

import com.sanbang.bean.ezs_quality;

public interface ezs_qualityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_quality record);

    int insertSelective(ezs_quality record);

    ezs_quality selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_quality record);

    int updateByPrimaryKey(ezs_quality record);
}