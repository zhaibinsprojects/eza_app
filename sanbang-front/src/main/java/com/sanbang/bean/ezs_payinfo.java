package com.sanbang.bean;

import java.math.BigDecimal;

public class ezs_payinfo {
    private Long id;
    private String addTime;

    private Boolean deleteStatus;
 // 订单编号
    private String order_no;
 // 1.采购单，2.订单
    private Integer order_type;
 // 方式 1.线下 2.线上
    private Integer pay_mode;
 // 流水号
    private String pay_no;
 // 金额
    private BigDecimal price;

    private Long bill_id;
 // 支付人
    private Long paymentUser_id;
 // 收款人
    private Long receUser_id;
    
    private Integer acount;
	private BigDecimal aprice;
	private String name;
	

	public Integer getAcount() {
		return acount;
	}

	public void setAcount(Integer acount) {
		this.acount = acount;
	}

	public BigDecimal getAprice() {
		return aprice;
	}

	public void setAprice(BigDecimal aprice) {
		this.aprice = aprice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public Integer getOrder_type() {
        return order_type;
    }

    public void setOrder_type(Integer order_type) {
        this.order_type = order_type;
    }

    public Integer getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(Integer pay_mode) {
        this.pay_mode = pay_mode;
    }

    public String getPay_no() {
        return pay_no;
    }

    public void setPay_no(String pay_no) {
        this.pay_no = pay_no == null ? null : pay_no.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getBill_id() {
        return bill_id;
    }

    public void setBill_id(Long bill_id) {
        this.bill_id = bill_id;
    }

    public Long getPaymentUser_id() {
        return paymentUser_id;
    }

    public void setPaymentUser_id(Long paymentUser_id) {
        this.paymentUser_id = paymentUser_id;
    }

    public Long getReceUser_id() {
        return receUser_id;
    }

    public void setReceUser_id(Long receUser_id) {
        this.receUser_id = receUser_id;
    }
}