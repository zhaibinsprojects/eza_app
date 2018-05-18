package com.sanbang.dao;

import com.sanbang.bean.ezs_customized;

public interface ezs_customizedMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ezs_customized record);

    int insertSelective(ezs_customized record);

    ezs_customized selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ezs_customized record);

    int updateByPrimaryKey(ezs_customized record);
}