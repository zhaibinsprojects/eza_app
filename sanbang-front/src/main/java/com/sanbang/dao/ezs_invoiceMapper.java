package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_invoice;

@Repository
public interface ezs_invoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_invoice record);

    int insertSelective(ezs_invoice record);

    ezs_invoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_invoice record);

    int updateByPrimaryKey(ezs_invoice record);
}