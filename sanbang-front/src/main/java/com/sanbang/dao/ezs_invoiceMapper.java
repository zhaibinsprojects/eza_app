package com.sanbang.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.utils.Page;

@Repository
public interface ezs_invoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_invoice record);

    int insertSelective(ezs_invoice record);

    ezs_invoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_invoice record);

    int updateByPrimaryKey(ezs_invoice record);

	ezs_invoice selectInvoiceByOrderNo(String orderno);

	List<ezs_invoice> getInvoiceBySeller(@Param("startTime")Date dt1, @Param("endTime")Date dt2,@Param("page")Page page, @Param("userId")Long userId,@Param("type")int type,@Param("order_no")String order_no);

	int getInvoiceCountForSeller(@Param("startTime")Date dt1, @Param("endTime")Date dt2,@Param("page")Page page, @Param("userId")Long userId,@Param("type")int type,@Param("order_no")String order_no);
	
	int getInvoiceCountForBuyer(@Param("userId")Long userId,@Param("type")int type);

	List<ezs_invoice> getInvoiceForBuyer(@Param("page")Page page, @Param("userId")Long userId,@Param("type")int type);
}