package com.sanbang.dao;

import com.sanbang.bean.ezs_customized_record;

public interface ezs_customized_recordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ezs_customized_record record);

    int insertSelective(ezs_customized_record record);

    ezs_customized_record selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ezs_customized_record record);

    int updateByPrimaryKey(ezs_customized_record record);
}