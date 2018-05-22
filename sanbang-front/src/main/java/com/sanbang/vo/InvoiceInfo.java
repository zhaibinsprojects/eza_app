package com.sanbang.vo;

import com.sanbang.bean.ezs_invoice;

public class InvoiceInfo extends ezs_invoice {
	
	private Long pId;
	private String pext;
	private String pname;
	private String path;
	public Long getpId() {
		return pId;
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
	public String getPext() {
		return pext;
	}
	public void setPext(String pext) {
		this.pext = pext;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public InvoiceInfo(Long pId, String pext, String pname, String path) {
		super();
		this.pId = pId;
		this.pext = pext;
		this.pname = pname;
		this.path = path;
	}
	public InvoiceInfo() {
		super();
	}
	
}
