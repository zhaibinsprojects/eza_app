package com.sanbang.utils;

import java.io.Serializable;

/**
 * 数说管理后台分页
 * @author langjf
 *
 */
public class InstructionPager  implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8365603064731471012L;

	private String title;
	private String username;
	private int status=20;

	private int pageSize = 8;
	private int pageCount = 1;
	private int pageNo = 1;
	private int totolPage = 1;
	//当前条数
	private int page;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getTotolPage() {
		return totolPage;
	}
	public void setTotolPage(int totolPage) {
		this.totolPage = totolPage;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
}
