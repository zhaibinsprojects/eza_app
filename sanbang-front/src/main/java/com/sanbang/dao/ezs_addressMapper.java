package com.sanbang.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;


import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_address;


public interface ezs_addressMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_address record);

    int insertSelective(ezs_address record);

    ezs_address selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_address record);

    ezs_address findByAreaidAndAreainfoAnduserid(@Param("area_id")Long areaid, @Param("area_info")String areainfo, @Param("user_id")Long userid);

	List<ezs_address> selectByUserId(Long userid);



    int updateByPrimaryKey(ezs_address record);
    
    List<ezs_address> getAddressByUserId(Long userId);
}