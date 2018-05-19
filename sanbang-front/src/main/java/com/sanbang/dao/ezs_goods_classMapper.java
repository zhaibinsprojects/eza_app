package com.sanbang.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_class;

@Repository
public interface ezs_goods_classMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_goods_class record);

    int insertSelective(ezs_goods_class record);

    ezs_goods_class selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_goods_class record);

    int updateByPrimaryKey(ezs_goods_class record);
    
    List<ezs_goods_class> selectAllGoodClass();
    
    List<ezs_goods_class> selectAllGoodClassByLevel(String level);
}