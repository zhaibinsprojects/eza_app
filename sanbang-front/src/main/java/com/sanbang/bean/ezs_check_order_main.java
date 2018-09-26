package com.sanbang.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ezs_check_order_main implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date addTime;

	private Boolean deleteStatus;

	private Date lastModifyDate;// 商品更新日期，即最近一次修改的日期

	private String order_no;// 订单编号

	private BigDecimal order_money;// 订单金额

	private BigDecimal imblance_money;// 应付、应退金额,值>0:表示实际付款金额较少，需补交；值<0：表示实际付款金额较多，需退款。

	private String memo;// 备注

	private String username;// 联系人

	private String linkphone;// 联系电话

	List<ezs_check_order_items> items;// 对账单历史记录详情

	List<ezs_checkm_photo> acclist;

	public List<ezs_checkm_photo> getAcclist() {
		return acclist;
	}

	public void setAcclist(List<ezs_checkm_photo> acclist) {
		this.acclist = acclist;
	}

	public List<ezs_check_order_items> getItems() {
		return items;
	}

	public void setItems(List<ezs_check_order_items> items) {
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Boolean getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Date getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no == null ? null : order_no.trim();
	}

	public BigDecimal getOrder_money() {
		return order_money;
	}

	public void setOrder_money(BigDecimal order_money) {
		this.order_money = order_money;
	}

	public BigDecimal getImblance_money() {
		return imblance_money;
	}

	public void setImblance_money(BigDecimal imblance_money) {
		this.imblance_money = imblance_money;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo == null ? null : memo.trim();
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLinkphone() {
		return linkphone;
	}

	public void setLinkphone(String linkphone) {
		this.linkphone = linkphone;
	}

	
	
	public ezs_check_order_main(Long id, Date addTime, Boolean deleteStatus, Date lastModifyDate, String order_no,
			BigDecimal order_money, BigDecimal imblance_money, String memo, String username, String linkphone,
			List<ezs_check_order_items> items, List<ezs_checkm_photo> acclist) {
		super();
		this.id = id;
		this.addTime = addTime;
		this.deleteStatus = deleteStatus;
		this.lastModifyDate = lastModifyDate;
		this.order_no = order_no;
		this.order_money = order_money;
		this.imblance_money = imblance_money;
		this.memo = memo;
		this.username = username;
		this.linkphone = linkphone;
		this.items = items;
		this.acclist = acclist;
	}

	public ezs_check_order_main() {
		// TODO Auto-generated constructor stub
	}

}