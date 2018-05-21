package com.sanbang.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_invoice;
import com.sanbang.bean.ezs_pact;
import com.sanbang.utils.Page;

@Repository
public interface ezs_pactMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_pact record);

    int insertSelective(ezs_pact record);

    ezs_pact selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_pact record);

    int updateByPrimaryKey(ezs_pact record);

	List<ezs_pact> selectPactBySellerId(Long sellerUser_id);

	ezs_invoice selectPactByOrderNo(String orderno);

	List<ezs_pact> selectPactByDate(@Param("startTime")Date dt1, @Param("endTime")Date dt2, @Param("sellerId")Long sellerId,@Param("page")Page page);

	int selectCountById(Long sellerId);

	List<ezs_pact> queryPact(Page page, Long sellerId);

}