package com.sanbang.utils;

/**
 * 相关指数管理pager
 * 
 * @author LENOVO
 *
 */
public class Mapexponentrepager {
	private long cataId = 29; // 标题id
	private int type = 23;// 指数管理1相关指数管理2

	private int pageSize = 8;// 显示几条

	private int pisuser = 1;// 是否再用
	private int isuser = 1;
	private int page;// 从第几条开始

	private int pageNo = 1;// 当前页
	private long totalpage = 1;

	public long getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
	}

	private int pageCount = 1;

	public long getCataId() {
		return cataId;
	}

	public void setCataId(long cataId) {
		this.cataId = cataId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPisuser() {
		return pisuser;
	}

	public void setPisuser(int pisuser) {
		this.pisuser = pisuser;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getIsuser() {
		return isuser;
	}

	public void setIsuser(int isuser) {
		this.isuser = isuser;
	}

}
