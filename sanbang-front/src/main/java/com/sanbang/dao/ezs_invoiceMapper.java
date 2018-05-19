package com.sanbang.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_pact;

@Repository
public interface ezs_invoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_invoice record);

    int insertSelective(ezs_invoice record);

    ezs_invoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_invoice record);

    int updateByPrimaryKey(ezs_invoice record);

	List<ezs_invoice> selectInvoiceListInfoById(Long userId);

	int selectCount();

	ezs_invoice selectInvoiceInfoById(Long invoiceId);

	ezs_invoice selectInvoiceByOrderNo(String orderno);

	List<ezs_invoice> selectInvoiceByDate(@Param("startTime")Date dt1, @Param("endTime")Date dt2);
}