package com.sanbang.bin;

import java.io.Serializable;


/**
 * 用户信息实体类
 * 
 * @author zhangxiantao
 *  
 * 2016年7月28日
 */
public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2369482733976548326L;
	
	private String userName;			// 用户名
	private String password;			// 用户密码
	private String password1;			// 默认密码
	private String passwordFlag;		// 0:初始密码,1:修改过密码

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword1() {
		return password1;
	}
	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	public String getPasswordFlag() {
		return passwordFlag;
	}
	public void setPasswordFlag(String passwordFlag) {
		this.passwordFlag = passwordFlag;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((password1 == null) ? 0 : password1.hashCode());
		result = prime * result
				+ ((passwordFlag == null) ? 0 : passwordFlag.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (password1 == null) {
			if (other.password1 != null)
				return false;
		} else if (!password1.equals(other.password1))
			return false;
		if (passwordFlag == null) {
			if (other.passwordFlag != null)
				return false;
		} else if (!passwordFlag.equals(other.passwordFlag))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
}
