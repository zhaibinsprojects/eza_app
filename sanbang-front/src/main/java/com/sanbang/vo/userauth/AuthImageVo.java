package com.sanbang.vo.userauth;

import java.io.Serializable;
import java.util.Date;


/**
 * 资质图片存储
 */
public class AuthImageVo implements Serializable {

	private static final long serialVersionUID = 737992306918047050L;

	private String imgcode;// 标识

	private String imgurl;// 地址

	private Date usetime;// 时间

	private String name;// 名称
	
	private long accid;

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

	public Date getUsetime() {
		return usetime;
	}

	public void setUsetime(Date usetime) {
		this.usetime = usetime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public long getAccid() {
		return accid;
	}

	public void setAccid(long accid) {
		this.accid = accid;
	}

	@Override
	public String toString() {
		return "AuthImageVo [imgcode=" + imgcode + ", imgurl=" + imgurl + ", usetime=" + usetime + ", name=" + name
				+ ", accid=" + accid + "]";
	}

	

}
