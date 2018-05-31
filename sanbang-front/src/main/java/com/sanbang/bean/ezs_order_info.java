package com.sanbang.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单列表
 * 
 * @author LENOVO 全款： 应付金额=实际付款金额 all_price ; 非全款 应付金额=first_price+end_price
 *         实际付款金额=first_price+end_price-adjust_price ; 订单总金额 =total_price
 */
public class ezs_order_info implements Serializable {


	private static final long serialVersionUID = 5024357524788189125L;

	private String good_no;// 商品编号

	private String addTime;// 订单时间

	private boolean deleteStatus;

	private BigDecimal price;// 单价
	// 数量
	private double count;

	// 订单编号
	private String order_no;

	private String name;// 商品名称

	private String addess;// 库存所在地
	// 库存所地区名称
	private String areaName;

	private Long area_id;// 库存所地区
	// 订单类型 ORDER_SELF_GOOD.自营商品订单 2=ORDER_MATCH_GOOD.撮合商品订单
	private String order_type;
	// 总价
	private BigDecimal total_price;

	// 商品量
	private BigDecimal goods_amount;

	// 1待处理 订单状态10.待确认 20.待签约 30.待付款 40.首款待支付 50.首款待确认 60.待发货
	// 70.已发货 80.尾款待支付 90.尾款待确认 100.退货中 110.已完成 120.已退货 130.已取消
	private Integer order_status;

	// 首付款
	private BigDecimal first_price;

	// 尾款
	private BigDecimal end_price;

	// 全款
	private BigDecimal all_price;

	// 合同状态 1.纸质 2.电子
	private int pact_status;
	// 订单完成时
	private String finishtime;
	// 收货地址id
	private Long address_id;

	private long buyerid;

	private long sellerid;

	// 支付方式（0.全款，1：首款+尾款 ）
	// @Column(columnDefinition = "int default 0")
	private int pay_mode;

	// 线上付款 线下付款（1.线上，2.线下）（首款）
	// 商品量
	// @Column(columnDefinition = "int default 1")
	private int pay_mode01;

	// 线上付款 线下付款（1.线上，2.线下）（尾款）
	// 商品量
	// @Column(columnDefinition = "int default 1")
	private int pay_mode02;

	// 运送状态
	// @Column(columnDefinition = "int default 0")
	private int sc_status;

	// 运费信息
	private ezs_logistics ezs_logistics;
	
	//图片信息
	private String path;
	
	
	public String getGood_no() {
		return good_no;
	}

	public void setGood_no(String good_no) {
		this.good_no = good_no;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public boolean isDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddess() {
		return addess;
	}

	public void setAddess(String addess) {
		this.addess = addess;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getArea_id() {
		return area_id;
	}

	public void setArea_id(Long area_id) {
		this.area_id = area_id;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public BigDecimal getTotal_price() {
		return total_price;
	}

	public void setTotal_price(BigDecimal total_price) {
		this.total_price = total_price;
	}

	public BigDecimal getGoods_amount() {
		return goods_amount;
	}

	public void setGoods_amount(BigDecimal goods_amount) {
		this.goods_amount = goods_amount;
	}

	public Integer getOrder_status() {
		return order_status;
	}

	public void setOrder_status(Integer order_status) {
		this.order_status = order_status;
	}

	public BigDecimal getFirst_price() {
		return first_price;
	}

	public void setFirst_price(BigDecimal first_price) {
		this.first_price = first_price;
	}

	public BigDecimal getEnd_price() {
		return end_price;
	}

	public void setEnd_price(BigDecimal end_price) {
		this.end_price = end_price;
	}

	public BigDecimal getAll_price() {
		return all_price;
	}

	public void setAll_price(BigDecimal all_price) {
		this.all_price = all_price;
	}

	public int getPact_status() {
		return pact_status;
	}

	public void setPact_status(int pact_status) {
		this.pact_status = pact_status;
	}

	public String getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(String finishtime) {
		this.finishtime = finishtime;
	}

	public Long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Long address_id) {
		this.address_id = address_id;
	}

	public long getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(long buyerid) {
		this.buyerid = buyerid;
	}

	public long getSellerid() {
		return sellerid;
	}

	public void setSellerid(long sellerid) {
		this.sellerid = sellerid;
	}

	public int getPay_mode() {
		return pay_mode;
	}

	public void setPay_mode(int pay_mode) {
		this.pay_mode = pay_mode;
	}

	public int getPay_mode01() {
		return pay_mode01;
	}

	public void setPay_mode01(int pay_mode01) {
		this.pay_mode01 = pay_mode01;
	}

	public int getPay_mode02() {
		return pay_mode02;
	}

	public void setPay_mode02(int pay_mode02) {
		this.pay_mode02 = pay_mode02;
	}

	public int getSc_status() {
		return sc_status;
	}

	public void setSc_status(int sc_status) {
		this.sc_status = sc_status;
	}

	public ezs_logistics getEzs_logistics() {
		return ezs_logistics;
	}

	public void setEzs_logistics(ezs_logistics ezs_logistics) {
		this.ezs_logistics = ezs_logistics;
	}

	@Override
	public String toString() {
		return "ezs_order_info [good_no=" + good_no + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus
				+ ", price=" + price + ", count=" + count + ", order_no=" + order_no + ", name=" + name + ", addess="
				+ addess + ", areaName=" + areaName + ", area_id=" + area_id + ", order_type=" + order_type
				+ ", total_price=" + total_price + ", goods_amount=" + goods_amount + ", order_status=" + order_status
				+ ", first_price=" + first_price + ", end_price=" + end_price + ", all_price=" + all_price
				+ ", pact_status=" + pact_status + ", finishtime=" + finishtime + ", address_id=" + address_id
				+ ", buyerid=" + buyerid + ", sellerid=" + sellerid + ", pay_mode=" + pay_mode + ", pay_mode01="
				+ pay_mode01 + ", pay_mode02=" + pay_mode02 + ", sc_status=" + sc_status + ", ezs_logistics="
				+ ezs_logistics + ", path=" + path + "]";
	}
	

	

//	@Override
//	public String toString() {
//		return "ezs_order_info [good_no=" + good_no + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus
//				+ ", price=" + price + ", count=" + count + ", order_no=" + order_no + ", name=" + name + ", addess="
//				+ addess + ", areaName=" + areaName + ", area_id=" + area_id + ", order_type=" + order_type
//				+ ", total_price=" + total_price + ", goods_amount=" + goods_amount + ", order_status=" + order_status
//				+ ", first_price=" + first_price + ", end_price=" + end_price + ", all_price=" + all_price
//				+ ", pact_status=" + pact_status + ", finishtime=" + finishtime + ", address_id=" + address_id
//				+ ", buyerid=" + buyerid + ", sellerid=" + sellerid + ", pay_mode=" + pay_mode + ", pay_mode01="
//				+ pay_mode01 + ", pay_mode02=" + pay_mode02 + ", sc_status=" + sc_status + ", ezs_logistics="
//				+ ezs_logistics + "]";
//	}

}
