package com.sanbang.vo;

import java.util.List;

import com.sanbang.bean.ezs_address;
import com.sanbang.bean.ezs_user;
import com.sanbang.bean.ezs_userinfo;
/**
 * 用户信息表，包含地址、联系电话等信息
 * @author LENOVO
 *
 */
public class UserInfoMess {
	private ezs_user user;
	private List<ezs_address> userAddress;
	private List<String> addressMess;
	
	public List<String> getAddressMess() {
		return addressMess;
	}
	public void setAddressMess(List<String> addressMess) {
		this.addressMess = addressMess;
	}
	public ezs_user getUser() {
		return user;
	}
	public void setUser(ezs_user user) {
		this.user = user;
	}
	public List<ezs_address> getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(List<ezs_address> userAddress) {
		this.userAddress = userAddress;
	}
	public UserInfoMess(ezs_user user, List<ezs_address> userAddress) {
		super();
		this.user = user;
		this.userAddress = userAddress;
	}
	public UserInfoMess() {
		super();
	}
}
