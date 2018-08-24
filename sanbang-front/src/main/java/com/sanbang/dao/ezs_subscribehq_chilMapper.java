package com.sanbang.dao;

import com.sanbang.bean.ezs_subscribehq_chil;

public interface ezs_subscribehq_chilMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_subscribehq_chil record);

    int insertSelective(ezs_subscribehq_chil record);

    ezs_subscribehq_chil selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_subscribehq_chil record);

    int updateByPrimaryKey(ezs_subscribehq_chil record);
}