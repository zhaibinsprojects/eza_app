package com.sanbang.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_pact;
import com.sanbang.vo.InvoiceInfo;
import com.sanbang.utils.Page;
import com.sanbang.vo.GoodsInfo;
import com.sanbang.vo.InvoiceInfo;

@Repository
public interface ezs_invoiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_invoice record);

    int insertSelective(ezs_invoice record);

    ezs_invoice selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_invoice record);

    int updateByPrimaryKey(ezs_invoice record);

	Map<String, Object> selectInvoiceListInfoById(Long userId, String currentPage);

	ezs_invoice selectInvoiceInfoById(Long invoiceId);

	ezs_invoice selectInvoiceByOrderNo(String orderno);

	List<ezs_invoice> selectInvoiceByDate(@Param("startTime")Date dt1, @Param("endTime")Date dt2);
	
	List<InvoiceInfo> selectInvoiceByUser(Long userId);
	
	InvoiceInfo selectByPrimaryKeyTwo(Long id);
	
	List<ezs_invoice> selectInvoiceByDate(@Param("startTime")Date dt1, @Param("endTime")Date dt2,@Param("userId")long userId, @Param("page")Page page);

	int getInvoiceCountByUserId(Long userId);

	List<ezs_invoice> goodsInvoiceCountPage(@Param("page")Page page, @Param("userId")Long userId);
}