package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_subscribehq {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Integer creditUserd;//积分使用

    private String cycle;//订阅周期

    private Integer openmode;//开通状态（0，未开通；1，已开通 2：客服确认中）

    private BigDecimal payment;//支付金额

    private Integer paymode;//支付方式 0:线上，1:转账汇款

    private Integer subType;//用户类型（0，试用用户；1，已购买）

    private String subtotal;//订阅品类  ，分割

    private BigDecimal totalMoney;//实付金额

    private Long buyLog_id;

    private Long store_id;//商户

    private Long user_id;//用户
    
    private Long order_id;
    
    private ezs_memberorder memberorder;//订单支付记录

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

    public Integer getCreditUserd() {
        return creditUserd;
    }

    public void setCreditUserd(Integer creditUserd) {
        this.creditUserd = creditUserd;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle == null ? null : cycle.trim();
    }

    public Integer getOpenmode() {
        return openmode;
    }

    public void setOpenmode(Integer openmode) {
        this.openmode = openmode;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public Integer getPaymode() {
        return paymode;
    }

    public void setPaymode(Integer paymode) {
        this.paymode = paymode;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal == null ? null : subtotal.trim();
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Long getBuyLog_id() {
        return buyLog_id;
    }

    public void setBuyLog_id(Long buyLog_id) {
        this.buyLog_id = buyLog_id;
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

	public ezs_memberorder getMemberorder() {
		return memberorder;
	}

	public void setMemberorder(ezs_memberorder memberorder) {
		this.memberorder = memberorder;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}
    
}