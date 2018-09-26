package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_checkm_photo;

public interface ezs_checkm_photoMapper {
    int deleteByPrimaryKey(Long ckmid);

    int insert(ezs_checkm_photo record);

    int insertSelective(ezs_checkm_photo record);

    List<ezs_checkm_photo> selectByPrimaryKey(@Param("ckmid")Long ckmid);

    int updateByPrimaryKeySelective(ezs_checkm_photo record);

    int updateByPrimaryKey(ezs_checkm_photo record);
}