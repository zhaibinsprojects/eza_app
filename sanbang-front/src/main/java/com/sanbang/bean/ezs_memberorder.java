package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_memberorder {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String memberType;//产品类型(优享会员，尊享会员) 

    private BigDecimal totalMoney;//总金额

    private Integer creditUsed;//积分使用额

    private BigDecimal payAmount;//实付金额

    private Integer payMode;//支付方式 0:线上，1:转账汇款

    private Integer payState;//支付状态 0:未支付，1:已支付

    private String orderSource; //订单来源

    private Integer openStatu; //开通状态 0:未开通，1:已开通

    private Date startTime; //开始时间

    private Date endTime; //到期时间

    private String operator;//操作人

    private Long store_id;

    private String order_no;//订单编号

    private String voucher;//支付凭证

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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType == null ? null : memberType.trim();
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getCreditUsed() {
        return creditUsed;
    }

    public void setCreditUsed(Integer creditUsed) {
        this.creditUsed = creditUsed;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getPayMode() {
        return payMode;
    }

    public void setPayMode(Integer payMode) {
        this.payMode = payMode;
    }

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource == null ? null : orderSource.trim();
    }

    public Integer getOpenStatu() {
        return openStatu;
    }

    public void setOpenStatu(Integer openStatu) {
        this.openStatu = openStatu;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Long getStore_id() {
        return store_id;
    }

    public void setStore_id(Long store_id) {
        this.store_id = store_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher == null ? null : voucher.trim();
    }
}