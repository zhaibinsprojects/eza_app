package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_class_data_acquisition;

@Repository
public interface ezs_goods_class_data_acquisitionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods_class_data_acquisition record);

    int insertSelective(ezs_goods_class_data_acquisition record);

    ezs_goods_class_data_acquisition selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods_class_data_acquisition record);

    int updateByPrimaryKey(ezs_goods_class_data_acquisition record);
}