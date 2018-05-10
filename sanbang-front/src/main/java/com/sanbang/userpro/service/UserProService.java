package com.sanbang.userpro.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sanbang.bean.User_Proinfo;
import com.sanbang.bean.ezs_user;
import com.sanbang.utils.Result;


/**
 * 用户相关业务处理
 * 
 * @author zhangxiantao
 *  
 * 2016年7月28日
 */
public interface UserProService {

	/**
	 * 登录处理
	 * @param userName
	 * @param password
	 * @return
	 */

	Map<String, Object> login(String userName, String pd, String code,
			String userAgent, String ip, HttpServletRequest request,
			HttpServletResponse response, Integer flag) throws Exception;

	/**
	 * 检查公司名称
	 */
	public Map<String,Object> checkCompany(HttpServletRequest request,String company);
	
	/**
	 * 添加用户  用户注册
	 * @param username
	 * @param passwd
	 * @param mobile
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> userAdd(String username,String passwd,String passwdA,String mobile,String code,String myip,Integer flag,HttpSession session, HttpServletRequest request, String imgcode) throws Exception;
	
	/**
	 * 注册发送验证码
	 * @param phone
	 * @param string
	 * @return
	 */

	Map<String, Object> sendCode(String phone, String code,
			String mobilerecodestr, String mobilesendcodeexpirstr,
			String mobileintervalstr, String mobilesendtimesstr,Integer flag,String content);
	
	/**
	 * 忘记密码 发送验证码
	 * @param phone
	 * @param string
	 * @return
	 */
	public Map<String, Object> sendFtCode(String phone, String string) throws Exception;
	
	/**
	 * 检查验证码
	 * @param mobile
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	Map<String, Object> checkFtCode(String mobile, String code,HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 修改密码
	 * @param passwd
	 * @param passwdA
	 * @param session
	 * @return
	 */
	public Map<String, Object> chgPasswd(String passwd,String passwdA,HttpServletRequest request) throws Exception;
	
	/**
	 * 检查用户是否存在
	 * @param userName
	 * @return
	 */
	public Map<String, Object> checkUserName(String userName) throws Exception;
	
	/**
	 * 检查手机号码是否存在
	 * @param userName
	 * @return
	 */
	Result checkMobile(String mobile)throws Exception;
	
	/**
	 * 根据用户名查询用户信息
	 * @param userName
	 * @return
	 */
	public ezs_user getUserInfoByUserName(String userName);
	
    /**
     * 发送修改手机号码的验证码
     * @param phone
     * @param code
     * @return
     */
	Map<String, Object> sendUpMoCode(String phone, String code)  throws Exception;


	/**
	 * 检查用户修改手机号前的验证码验证 及成功后的  更新 数据库  
	 * @param mobile
	 * @param code
	 * @param upi
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> checkUpMoCode(String mobile, String code,User_Proinfo upi, HttpServletRequest request)  throws Exception;


	/**
	 * 用户退出
	 * @param upi
	 * @return
	 * @throws Exception
	 */

	boolean userLogot(User_Proinfo upi, String cookieuserkey) throws Exception;
	
	/**
	 * 验证登陆的图片验证码
	 * @param randImgCode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> loginRandImgVali(String randImgCode,HttpServletRequest request) throws Exception;

	Map<String, Object> sendContractCode(String phone, String code, Integer flag);

	Map<String, Object> checkSCCode(String mobile, String code)
			throws Exception;

	String getUserMessage(String username);

	Object updateUserSessionInfo(String username, String mobile);


}
