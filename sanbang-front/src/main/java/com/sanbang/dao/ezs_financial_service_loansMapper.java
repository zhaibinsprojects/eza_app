package com.sanbang.dao;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_financial_service_loans;

@Repository
public interface ezs_financial_service_loansMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_financial_service_loans record);

    int insertSelective(ezs_financial_service_loans record);

    ezs_financial_service_loans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_financial_service_loans record);

    int updateByPrimaryKey(ezs_financial_service_loans record);
}