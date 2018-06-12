package com.sanbang.vo;

/**
 * order pager
 * @author LENOVO
 *
 */
public class PagerOrder {

	private int pageNow = 1; // 当前页数

	private int pageSize = 10; // 每页显示记录的条数

	private int totalCount; // 总的记录条数

	private int totalPageCount; // 总的页数

	private int secount;//查询节点
	
	//订单类型 10.自营商品订单20.撮合商品订单
	private String order_type;
	
	//订单状态10.待确认 20.待签约 30.待付款 40.首款待支付 50.首款待确认 60.待发货 
	//70.已发货 80.尾款待支付 90.尾款待确认 100.退货中 110.已完成 120.已退货 130.已取消
	private String order_status="-1";
	
	private long userid;//当前登录用户id

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getSecount() {
		return secount;
	}

	public void setSecount(int secount) {
		this.secount = secount;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}


	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}
	
	
}
