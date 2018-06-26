package com.sanbang.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sanbang.bean.ezs_address;
import com.sanbang.utils.Page;


public interface ezs_addressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_address record);

    int insertSelective(ezs_address record);

    ezs_address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_address record);

    ezs_address findByAreaidAndAreainfoAnduserid(@Param("area_id")Long areaid, @Param("area_info")String areainfo, @Param("user_id")Long userid);

    List<ezs_address> selectByUserId(Map<String, Object> map);



    int updateByPrimaryKey(ezs_address record);
    
    List<ezs_address> getAddressByUserId(Long userId);

	int getCountAddressByUserId(Long userid);

	ezs_address findAddressByUseridAndBes(@Param("userId")Long userId, @Param("bestow")Boolean addressBestow);
}