package com.sanbang.vo.userauth;

import java.io.Serializable;

public class AuthImageVo implements Serializable {
	/**
	 * 资质图片存储
	 */
	private static final long serialVersionUID = 2007850615499984488L;

	private String imgcode;//标识

	private String imgurl;//地址

	public String getImgcode() {
		return imgcode;
	}

	public void setImgcode(String imgcode) {
		this.imgcode = imgcode;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	@Override
	public String toString() {
		return "AuthImageVo [imgcode=" + imgcode + ", imgurl=" + imgurl + "]";
	}

}
