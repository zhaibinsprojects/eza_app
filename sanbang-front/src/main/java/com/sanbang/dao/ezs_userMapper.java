package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_user;
import com.sanbang.bin.UserInfo;

@Repository("ezs_userMapper")
public interface ezs_userMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_user record);

    int insertSelective(ezs_user record);

    ezs_user selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_user record);

    int updateByPrimaryKey(ezs_user record);


	

}