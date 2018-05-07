package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_customer_paper;

@Repository
public interface ezs_customer_paperMapper {
    int insert(ezs_customer_paper record);

    int insertSelective(ezs_customer_paper record);
}