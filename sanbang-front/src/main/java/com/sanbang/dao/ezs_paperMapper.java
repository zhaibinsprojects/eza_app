package com.sanbang.dao;

import com.sanbang.bean.ezs_paper;

public interface ezs_paperMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_paper record);

    int insertSelective(ezs_paper record);

    ezs_paper selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_paper record);

    int updateByPrimaryKey(ezs_paper record);
    
}