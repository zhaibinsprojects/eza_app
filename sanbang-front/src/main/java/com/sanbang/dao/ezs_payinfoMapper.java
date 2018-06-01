package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_payinfo;

@Repository
public interface ezs_payinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_payinfo record);

    int insertSelective(ezs_payinfo record);

    ezs_payinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_payinfo record);

    int updateByPrimaryKey(ezs_payinfo record);

	ezs_payinfo selectByBillId(Long billId);
	
	List<ezs_payinfo> getpayinfoByOrderno(String order_no);

	List<ezs_payinfo> selectPayRecordTotal(Map<String, Object> map);

	List<ezs_payinfo> selectPayRecord(Map<String, Object> map);

	int selectCount(Map<String, Object> map);
}