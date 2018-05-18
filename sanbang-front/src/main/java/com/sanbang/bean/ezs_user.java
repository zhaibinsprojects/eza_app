package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.sanbang.vo.userauth.AuthImageVo;

/**
 * 用户登陆信息
 * 
 * @author langjf
 *
 */
public class ezs_user implements Serializable {

	private static final long serialVersionUID = -2710575667615236430L;

	private Long id;

	private Date addTime;

	private Boolean deleteStatus;

	private Date lastLoginDate;

	private String lastLoginIp;

	private Integer loginCount;

	private Date loginDate;

	private String loginIp;

	private String name;

	private String password;

	private String trueName;//真实名称

	private String userRole;//角色

	private Long parent_id;

	private Long store_id;

	private Long userInfo_id;

	// 用户在redis的key
	private String userkey;
	// 认证用户标识
	private String authkey;

	private boolean isAvatar;// 是否主账户 true 是 fasle否

	private ezs_userinfo ezs_userinfo;// 个人信息

	private ezs_store ezs_store;// 公司资料信息

	private ezs_bill ezs_bill;// 发票信息

	private List<AuthImageVo> authimg;// 资质存储

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Boolean getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getUserInfo_id() {
		return userInfo_id;
	}

	public void setUserInfo_id(Long userInfo_id) {
		this.userInfo_id = userInfo_id;
	}

	public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

	public String getAuthkey() {
		return authkey;
	}

	public void setAuthkey(String authkey) {
		this.authkey = authkey;
	}

	public boolean isAvatar() {
		return isAvatar;
	}

	public void setAvatar(boolean isAvatar) {
		this.isAvatar = isAvatar;
	}

	public ezs_userinfo getEzs_userinfo() {
		return ezs_userinfo;
	}

	public void setEzs_userinfo(ezs_userinfo ezs_userinfo) {
		this.ezs_userinfo = ezs_userinfo;
	}

	public ezs_store getEzs_store() {
		return ezs_store;
	}

	public void setEzs_store(ezs_store ezs_store) {
		this.ezs_store = ezs_store;
	}

	public ezs_bill getEzs_bill() {
		return ezs_bill;
	}

	public void setEzs_bill(ezs_bill ezs_bill) {
		this.ezs_bill = ezs_bill;
	}

	public List<AuthImageVo> getAuthimg() {
		return authimg;
	}

	public void setAuthimg(List<AuthImageVo> authimg) {
		this.authimg = authimg;
	}

	@Override
	public String toString() {
		return "ezs_user [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus + ", lastLoginDate="
				+ lastLoginDate + ", lastLoginIp=" + lastLoginIp + ", loginCount=" + loginCount + ", loginDate="
				+ loginDate + ", loginIp=" + loginIp + ", name=" + name + ", password=" + password + ", trueName="
				+ trueName + ", userRole=" + userRole + ", parent_id=" + parent_id + ", store_id=" + store_id
				+ ", userInfo_id=" + userInfo_id + ", userkey=" + userkey + ", authkey=" + authkey + ", isAvatar="
				+ isAvatar + ", ezs_userinfo=" + ezs_userinfo + ", ezs_store=" + ezs_store + ", ezs_bill=" + ezs_bill
				+ ", authimg=" + authimg + "]";
	}

}