package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
    
    List<ezs_financial_service_loans> selectLoanByUser(@Param("user_id")long user_id,@Param("pageCount")int pageCount,@Param("pageNow")int pageNow);
}