package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;

public class ezs_user  extends ezs_userinfo implements Serializable{
    /**
	 * //用户登陆信息
	 */
	private static final long serialVersionUID = -1598895147863170074L;

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

    private String trueName;

    private String userRole;

    private Long parent_id;

    private Long store_id;

    private Long userInfo_id;
    
 // 用户在redis的key
  	private String userkey;

    public String getUserkey() {
		return userkey;
	}

	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}

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
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
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
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
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

	@Override
	public String toString() {
		return "ezs_user [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus + ", lastLoginDate="
				+ lastLoginDate + ", lastLoginIp=" + lastLoginIp + ", loginCount=" + loginCount + ", loginDate="
				+ loginDate + ", loginIp=" + loginIp + ", name=" + name + ", password=" + password + ", trueName="
				+ trueName + ", userRole=" + userRole + ", parent_id=" + parent_id + ", store_id=" + store_id
				+ ", userInfo_id=" + userInfo_id + ", userkey=" + userkey + "]";
	}
    
    
}