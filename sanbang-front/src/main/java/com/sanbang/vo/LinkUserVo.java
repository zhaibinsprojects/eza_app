package com.sanbang.vo;

import java.io.Serializable;

public class LinkUserVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4896054626121278779L;

	
	private String userimg;// 头像

	private String username;// 登陆名称

	private String loginphone;// 登陆手机号

	private String truename;// 真实名称
	
	
	

	private long sex;// 1 男 2女
	
	private String sexval;

	private long position;// 职位 部门
	
	private String positionval;

	private String tel;// 固定电话
	
	private String phone;//手机

	private String email;// email

	private String qq; // qq

	public String getUserimg() {
		return userimg;
	}

	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLoginphone() {
		return loginphone;
	}

	public void setLoginphone(String loginphone) {
		this.loginphone = loginphone;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public long getSex() {
		return sex;
	}

	public void setSex(long sex) {
		this.sex = sex;
	}

	public String getSexval() {
		return sexval;
	}

	public void setSexval(String sexval) {
		this.sexval = sexval;
	}

	public long getPosition() {
		return position;
	}

	public void setPosition(long position) {
		this.position = position;
	}

	public String getPositionval() {
		return positionval;
	}

	public void setPositionval(String positionval) {
		this.positionval = positionval;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public LinkUserVo() {
		// TODO Auto-generated constructor stub
	}

	public LinkUserVo(String userimg, String username, String loginphone, String truename, long sex, String sexval,
			long position, String positionval, String tel, String phone, String email, String qq) {
		super();
		this.userimg = userimg;
		this.username = username;
		this.loginphone = loginphone;
		this.truename = truename;
		this.sex = sex;
		this.sexval = sexval;
		this.position = position;
		this.positionval = positionval;
		this.tel = tel;
		this.phone = phone;
		this.email = email;
		this.qq = qq;
	}	
	
}
	