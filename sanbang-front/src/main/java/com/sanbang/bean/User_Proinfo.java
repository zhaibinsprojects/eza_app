package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class User_Proinfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8692910143994480780L;

	public String name;// 用户登陆名称

	private String trueName;// 用户真实名称

	private String password;// 用户密码

	private String userRole;// ADMIN(后台用户)，BUYER(买家)，SELLER（卖家），若一个人两个角色（后台管理+买家）用ADMIN_BUYER

	// 一对一
	private ezs_userinfo userInfo;// 用户详情

	// private User parent;// 父类账号

	private ezs_store store;// 用户店铺

	// 上次登录时间
	private Date lastLoginDate;
	
	// 登录时间
	private Date loginDate;
	
	// 上次登录IP
	private String lastLoginIp;
	
	// 登录IP
	private String loginIp;

	// 登录次数
	private int loginCount;

	// 角色
	// ezs_user_role 联查
	private ezs_role roles;

	// 配置
	// @OneToOne(mappedBy = "user")
	private ezs_userconfig config;

	// 角色资源
	private Map<String, List<ezs_res>> roleResources;
	
	
	private String userkey;
	
	

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public ezs_userinfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(ezs_userinfo userInfo) {
		this.userInfo = userInfo;
	}

	public ezs_store getStore() {
		return store;
	}

	public void setStore(ezs_store store) {
		this.store = store;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public ezs_role getRoles() {
		return roles;
	}

	public void setRoles(ezs_role roles) {
		this.roles = roles;
	}

	public ezs_userconfig getConfig() {
		return config;
	}

	public void setConfig(ezs_userconfig config) {
		this.config = config;
	}

	public Map<String, List<ezs_res>> getRoleResources() {
		return roleResources;
	}

	public void setRoleResources(Map<String, List<ezs_res>> roleResources) {
		this.roleResources = roleResources;
	}

	@Override
	public String toString() {
		return "User_Proinfo [name=" + name + ", trueName=" + trueName + ", password=" + password + ", userRole="
				+ userRole + ", userInfo=" + userInfo + ", store=" + store + ", lastLoginDate=" + lastLoginDate
				+ ", loginDate=" + loginDate + ", lastLoginIp=" + lastLoginIp + ", loginIp=" + loginIp + ", loginCount="
				+ loginCount + ", roles=" + roles + ", config=" + config + ", roleResources=" + roleResources + "]";
	}

	
	
}
