package com.sanbang.vo.returnorder;

import java.math.BigDecimal;

public class ReturnOrderVO {

	private long id;// 退货订单id
	private long orderForm_id;// 订单id
	private String addTime;// 申请时间
	// buyer_daishouli(0,"退货待受理","","待受理",0),
	// buyer_yishouli(5,"已受理","","受理中",0),
	// buyer_querentuihuo(10,"已受理","","受理中",0),
	private Integer status;// 0提交退货申请，未受理，1确认受理， 2自营购买 3退货供应商

	private boolean deleteStatus;
	/**
	 * 平台对买方
	 */
	// buyer_tihuo(0,"提货中","待收货","受理中",0),
	// buyer_tihuozhong(5,"提货中","运输完成","受理中",0),
	// buyer_daishoukuan(15,"待收款","已退票","受理中",0),
	// buyer_daituipiao(20,"待收款","待退款","受理中",0),
	// buyer_finish(25,"退款完成","已退款","已完成",0),
	private String state1;// 对于买家展示

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 平台对卖方
	 */
	// seller_daituikuan(0,"待退款","待退款","受理中",0),
	// seller_tuikuandaiqueren(15,"退款待确认","退款待确认","受理中",0),
	// seller_tuikuanwancheng(20,"退款完成","已退款","受理中",0),
	// seller_yituipiao(25,"退款完成","已退票","受理中",0),
	// seller_finish(30,"交易完成","已完成","已完成",0);
	private String state2;// 对于卖家 展示

	private String set_return_no;// 退货编号

	private String returnReason;// 退货原因

	private String remark;// 补充说明

	private String prodectname;// 产品名称

	private BigDecimal num;// 数量

	private BigDecimal returnTotal;// 申请的退货金额

	private long userseller_id;// 卖家id
	private long user_id;// 申请id
	private Integer returnType;// 退货类型 0全部退货 1部分退货
	private String order_no;
	private long good_id;
	private BigDecimal price;// 单价

	public long getOrderForm_id() {
		return orderForm_id;
	}

	public void setOrderForm_id(long orderForm_id) {
		this.orderForm_id = orderForm_id;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getState1() {
		return state1;
	}

	public void setState1(String state1) {
		this.state1 = state1;
	}

	public String getState2() {
		return state2;
	}

	public void setState2(String state2) {
		this.state2 = state2;
	}

	public String getSet_return_no() {
		return set_return_no;
	}

	public void setSet_return_no(String set_return_no) {
		this.set_return_no = set_return_no;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProdectname() {
		return prodectname;
	}

	public void setProdectname(String prodectname) {
		this.prodectname = prodectname;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public BigDecimal getReturnTotal() {
		return returnTotal;
	}

	public void setReturnTotal(BigDecimal returnTotal) {
		this.returnTotal = returnTotal;
	}

	public long getUserseller_id() {
		return userseller_id;
	}

	public void setUserseller_id(long userseller_id) {
		this.userseller_id = userseller_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public Integer getReturnType() {
		return returnType;
	}

	public void setReturnType(Integer returnType) {
		this.returnType = returnType;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public long getGood_id() {
		return good_id;
	}

	public void setGood_id(long good_id) {
		this.good_id = good_id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

}
