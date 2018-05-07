package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_goods_photo;

@Repository
public interface ezs_goods_photoMapper {
    int insert(ezs_goods_photo record);

    int insertSelective(ezs_goods_photo record);
}