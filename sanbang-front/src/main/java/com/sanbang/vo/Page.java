package com.sanbang.vo;

/**
 * 分页查询的实体类
 * 
 * @author zhangxiantao
 *  
 * 2016年6月24日
 */
public class Page {
	
	private int pageSize = 10; //每页显示记录数
	private int totalPage;		//总页数
	private int totalCount;	//总记录数
	@SuppressWarnings("unused")
	private int total;
	private int pageNo;	//当前页
	private int currentIndex;	//当前记录起始索引
	private boolean entityOrField;	//true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	
	public int getTotal() {
		return totalCount;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		if(totalCount%pageSize==0)
			totalPage = totalCount/pageSize;
		else
			totalPage = totalCount/pageSize+1;
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNo() {
		if(pageNo<=0)
			pageNo = 1;
		if(pageNo>getTotalPage())
			pageNo = getTotalPage();
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentIndex() {
		currentIndex = (getPageNo()-1)*getPageSize();
		if(currentIndex<0)
			currentIndex = 0;
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public boolean isEntityOrField() {
		return entityOrField;
	}
	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}
	
}
