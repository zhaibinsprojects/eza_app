package com.sanbang.dao;

import com.sanbang.bean.ezs_customized_res;

public interface ezs_customized_resMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ezs_customized_res record);

    int insertSelective(ezs_customized_res record);

    ezs_customized_res selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ezs_customized_res record);

    int updateByPrimaryKey(ezs_customized_res record);
}