package com.sanbang.userpro.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sanbang.bean.ezs_store;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
import com.sanbang.utils.Result;
import com.sanbang.vo.LinkUserVo;


/**
 * 用户相关业务处理
 * 
 * @author langjf
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

	Result login(String userName, String pd, String code,
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
	public Result userAdd(String username,String passwd,String passwdA,String mobile,String code,String myip,Integer flag,HttpSession session, HttpServletRequest request,HttpServletResponse response) throws Exception;
	
	/**
	 * 注册发送验证码
	 * @param phone
	 * @param string
	 * @return
	 */

	Result sendCode(String phone, String code,
			String mobilerecodestr, String mobilesendcodeexpirstr,
			String mobileintervalstr, String mobilesendtimesstr,Integer flag,String content);
	
	/**
	 * 忘记密码 发送验证码
	 * @param phone
	 * @param string
	 * @return
	 */
	Result sendFtCode(String phone, String string) throws Exception;
	
	/**
	 * 检查验证码
	 * @param mobile
	 * @param code
	 * @return
	 * @throws Exception 
	 */
	Result checkFtCode(String mobile, String code,HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 修改密码
	 * @param passwd
	 * @param passwdA
	 * @param session
	 * @return
	 */
	Result chgPasswd(String passwd,String passwdA,HttpServletRequest request) throws Exception;
	
	/**
	 * 检查用户是否存在
	 * @param userName
	 * @return
	 */
	Result checkUserName(String userName) throws Exception;
	
	/**
	 * 检查手机号码是否存在
	 * @param userName
	 * @return
	 */
	Result checkMobile(String mobile)throws Exception;

	
    /**
     * 发送修改手机号码的验证码
     * @param phone
     * @param code
     * @return
     */
	Result sendUpMoCode(String phone, String code)  throws Exception;
	
	/**
	 * 检查用户修改手机号前的验证码验证
	 * @param mobile
	 * @param code
	 * @param upi
	 * @param request
	 * @return
	 */
	Result   checkUpMoCode(String mobile, String code,ezs_user upi, HttpServletRequest request) throws Exception;


	 /**
     * 发送修改手机号码的验证码
     * @param phone
     * @param code
     * @return
     */
	Result sendToUpMoCode(String phone, String code)  throws Exception;
	
	
	/**
	 * 检查用户修改手机号前的验证码验证 及成功后的  更新 数据库  
	 * @param mobile
	 * @param code
	 * @param upi
	 * @return
	 * @throws Exception
	 */
	Result sendToUpMoPhoneCheck(String mobile, String code,ezs_user upi, HttpServletRequest request)  throws Exception;


	/**
	 * 用户退出
	 * @param upi
	 * @return
	 * @throws Exception
	 */

	boolean userLogot(ezs_user upi, String cookieuserkey) throws Exception;
	

	/**
	 * 检查修改密码前的验证码(忘记密码模块修改密码)
	 */
	Result checkSCCode(String mobile, String code)
			throws Exception;

	
	String getUserMessage(String username);

	Object updateUserSessionInfo(String username, String mobile);
	
	/**
	 * 分享码业务
	 */
    Result sharCodeTodo(String sharcode,String phone);

    /**
     * 
     * @param result
     * @param request
     * @param userRole 角色选择
     * @param companyName 公司名称
     * @param address 经营地址
     * @param area_id 经营地址
     * @param mianIndustry_id 主营行业
     * @param companyType_id 公司类型
     * @param trueName 姓名
     * @param sex_id 姓别
     * @param tel 固定电话
     * @param email 邮箱
     * @return 
     */
	Result userAddInfo(Result result, HttpServletRequest request, String userRole,
			String companyName, String address,
			String area_id, String mianIndustry_id, String companyType_id,
			String trueName, long sex_id, String tel,
			String email);

	/**
	 * 个人资料修改
	 * @param request
	 * @param ezsuser
	 * @param typeval
	 * @param linkvo
	 * @return
	 */
	Result upUserInfo(HttpServletRequest request,ezs_user ezsuser,String typeval,LinkUserVo linkvo);
	
	/**
	 * 修改公司信息
	 * @param request
	 * @param store
	 * @return
	 */
	Result upStoreInfo(HttpServletRequest request,ezs_store store,ezs_user upi);
	
}
