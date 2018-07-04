package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ezs_customized_res;

public interface ezs_customized_resMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customized_res record);

    int insertSelective(ezs_customized_res record);

    ezs_customized_res selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customized_res record);

    int updateByPrimaryKey(ezs_customized_res record);
    
    List<ezs_customized_res> selectByCustomizedId(Long customized_id);
}