package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ezs_customized;

public interface ezs_customizedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_customized record);

    int insertSelective(ezs_customized record);

    ezs_customized selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_customized record);

    int updateByPrimaryKey(ezs_customized record);
    
    List customizedList(Long user_id);
}