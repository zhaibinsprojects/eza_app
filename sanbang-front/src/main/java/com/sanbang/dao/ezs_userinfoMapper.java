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
    
    /**
     * 用户信息
     * @param username
     * @return
     */
    ezs_userinfo selectUserinfoByuserName(String username);
    
    /**
	 * 根据用户名查询用户的信息
	 * @param userName
	 * @return
	 */
    ezs_user getUserInfoByUserName(String userName);

	/**
	 * 修改密码
	 * @param userName
	 * @param password
	 * @return
	 */
	int modifyPassword(@Param("userName") String userName, @Param("password")  String password);
	
	
	
	/**
	 * 查询用户是否存在
	 * @param username
	 * @return
	 */
	List<ezs_user>     getUserInfoByUserNameFromBack(String username);

	/**
	 * 检查手机号
	 * @param mobile
	 * @return
	 */
	int checkMobile(String mobile);

	String getUserMessage(String username);

	/**
	 * 
	 * @param mp
	 * @return
	 */
	int updateUserPwdByMobile(Map<String, Object> mp);
}