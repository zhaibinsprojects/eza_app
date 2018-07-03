package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_column;

@Repository
public interface ezs_columnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_column record);

    int insertSelective(ezs_column record);

    ezs_column selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_column record);

    int updateByPrimaryKey(ezs_column record);
    
    List<ezs_column> getSecondThemeByFirstTheme(Long FirstThemeId);
    
}