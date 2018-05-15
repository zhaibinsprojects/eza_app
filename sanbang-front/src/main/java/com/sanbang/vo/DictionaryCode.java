package com.sanbang.vo;

public class DictionaryCode {
	
	
	
	
	/**
     * 请求成功
     */
    public static final int ERROR_WEB_REQ_SUCCESS = 101000;
	
	/**
     * 用户名不合法
     */
    public static final int ERROR_WEB_USER_NAME_NOT_LEGAL = 101001;
    /**
     * 用户名已被占用
     */
    public static final int ERROR_WEB_USER_NAME_OCCUPIED = 101002;
    /**
     * 密码不合法
     */
    public static final int ERROR_WEB_PASSWORD_NOT_LEGAL = 101003;
    /**
     * 确认密码不能为空
     */
    public static final int ERROR_WEB_PASSWORD_SAME_CHARACTER = 101004;
    /**
     * 手机号码已经被注册
     */
    public static final int ERROR_WEB_PHONE_TYPE_REGISTERED = 101007;
    
    /**
     * 手机号码已经被注册
     */
    public static final int ERROR_WEB_PASSWORD_NEQ_CHARACTER  = 101007;
    /**
     * 手机号码格式
     */
    public static final int ERROR_WEB_PHONE_NUMBER_REGISTERED = 101006;
    /**
     * 验证码错误
     */
    public static final int ERROR_WEB_CODE_ERROR = 101011;
    /**
     * 验证码不能为空
     */
    public static final int ERROR_WEB_CODE_NULL = 101012;
    /**
     * 用户不存在
     */
    public static final int ERROR_WEB_USER_NOT_EXIST = 101013;
    /**
     * 密码不正确
     */
    public static final int ERROR_WEB_PASSWORD_ERROR = 101014;
    /**
     * 注册成功
     */
    public static final int ERROR_WEB_REGIST_SUCCESS = 101015;
    /**
     * 注册失败
     */
    public static final int ERROR_WEB_REGIST_FAIL = 101016;
    /**
     * 存在的用户是子用户
     */
    public static final int ERROR_WEB_USER_IS_SUB = 101027;
    /**
     * 未绑定手机号
     */
    public static final int ERROR_WEB_PHONE_NUMBER_UNBOUND = 101060;
    /**
     * 服务器异常
     */
    public static final int ERROR_WEB_SERVER_ERROR = 199999;
    /**
     * 请等待0-60s后再次点击
     */
    public static final int ERROR_WEB_GET_VEIFY_CODE_ERROR = 101010;
    /**
     * 用户名与手机不匹配
     */
    public static final int ERROR_WEB_USER_NOT_MATCH_PHONE_NUMBER = 101009;
    /**
     * 用户名被多次注册
     */
    public static final int ERROR_WEB_USER_NAME_REGISTERED_MORE = 101019;
    /**
     * 获取短信验证码过于频繁
     */
    public static final int ERROR_WEB_GET_CODE_LIMIT = 101041;

    /**
     * 参数错误
     */
    public static final int ERROR_WEB_PARAM_ERROR = 110001;
    /**
     * access_tocken异常
     */
    public static final int ERROR_WEB_SESSION_ERROR = 110002;
    /**
     * access_tocken过期
     */
    public static final int ERROR_WEB_SESSION_EXPIRE = 110003;
    /**
     * 用户名或者密码错误
     */
    public static final int ERROR_WEB_USER_PASSWORD_ERROR = 110004;
    /**
     * appKey异常
     */
    public static final int ERROR_WEB_APPKEY_ERROR = 110005;
    /**
     * ip受限
     */
    public static final int ERROR_WEB_IP_LIMIT = 110006;
    /**
     * 供应商激活信息提交失败
     */
    public static final int ERROR_WEB_ACTIVATE_INFO_FAIL = 110007;
    /**
     * 供应商激活信息提交成功
     */
    public static final int ERROR_WEB_ACTIVATE_INFO_SUCCESS = 110008;
    /**
     * 查询合同列表成功
     */
    public static final int ERROR_WEB_QUERY_CONTRACT_LIST_SUCCESS = 110009;
    
    /**
     * 查询合同列表失败
     */
    public static final int ERROR_WEB_QUERY_CONTRACT_LIST_FAIL = 110010;
    
    /**
     * 查询合同详情失败
     */
    public static final int ERROR_WEB_QUERY_CONTRACT_INFO_FAIL = 110011;
    
    /**
     * 查询合同详情成功
     */
    public static final int ERROR_WEB_QUERY_CONTRACT_INFO_SUCCESS = 110012;
}
