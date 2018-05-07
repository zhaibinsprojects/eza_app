package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_order_pact;

@Repository
public interface ezs_order_pactMapper {
    int insert(ezs_order_pact record);

    int insertSelective(ezs_order_pact record);
}