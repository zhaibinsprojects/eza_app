package com.sanbang.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.bin.UserInfo;

@Repository("ezs_userMapper")
public interface ezs_userMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_user record);

    int insertSelective(ezs_user record);

    ezs_user selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_user record);

    int updateByPrimaryKey(ezs_user record);

    /**
	 * 查询用户是否存在
	 * @param username
	 * @return
	 */
	List<ezs_user>     getUserInfoByUserNameFromBack(String username);
	
	
	/**
	 * 查询用户是否存在
	 * @param username
	 * @return
	 */
	List<ezs_user>     getUserInfoByUserNameFromPhone(String phone);
	 /**
     * 用户信息
     * @param username
     * @return
     */
    ezs_userinfo selectUserinfoByuserName(String username);
    

	/**
	 * 修改密码
	 * @param userName
	 * @param password
	 * @return
	 */
	int modifyPassword(@Param("userName") String userName, @Param("password")  String password);
	
	

	/**
	 * 检查手机号
	 * @param mobile
	 * @return
	 */
	int checkMobile(String mobile);

	String getUserMessage(String username);
	
	public ezs_user getUserInfoByUserId(Long userId);

	

}