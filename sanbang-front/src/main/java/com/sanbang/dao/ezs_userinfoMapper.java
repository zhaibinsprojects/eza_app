package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;

@Repository(value="ezs_userinfoMapper")
public interface ezs_userinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_userinfo record);

    int insertSelective(ezs_userinfo record);

    ezs_userinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_userinfo record);

    int updateByPrimaryKey(ezs_userinfo record);
    
   
}