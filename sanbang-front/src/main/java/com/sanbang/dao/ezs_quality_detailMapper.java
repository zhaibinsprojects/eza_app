package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_quality_detail;

public interface ezs_quality_detailMapper {
    int insert(ezs_quality_detail record);

    int insertSelective(ezs_quality_detail record);
    
    List<ezs_quality_detail>  getDetailsByQid(@Param("id") long id);
}