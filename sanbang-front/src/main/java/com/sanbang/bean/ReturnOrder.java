package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ReturnOrder {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String guanliremark;

    private String guanliwuliumoeny;

    private BigDecimal num;

    private String prodectname;

    private String remark;

    private String returnAddress;

    private String returnReason;

    private Integer returnType;

    private String set_return_no;

    private String state1;

    private String state2;

    private BigDecimal tmoney;

    private BigDecimal tnum;

    private Integer type;

    private String updatetime;

    private Long upoper;

    private Long logistics_id;

    private Long orderForm_id;

    private String finshtime;

    private Integer order_status_store;

    private BigDecimal returnTotal;

    private Integer status;

    private Long upoper_id;

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

    public String getGuanliremark() {
        return guanliremark;
    }

    public void setGuanliremark(String guanliremark) {
        this.guanliremark = guanliremark == null ? null : guanliremark.trim();
    }

    public String getGuanliwuliumoeny() {
        return guanliwuliumoeny;
    }

    public void setGuanliwuliumoeny(String guanliwuliumoeny) {
        this.guanliwuliumoeny = guanliwuliumoeny == null ? null : guanliwuliumoeny.trim();
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getProdectname() {
        return prodectname;
    }

    public void setProdectname(String prodectname) {
        this.prodectname = prodectname == null ? null : prodectname.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getReturnAddress() {
        return returnAddress;
    }

    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress == null ? null : returnAddress.trim();
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
        this.returnType = returnType;
    }

    public String getSet_return_no() {
        return set_return_no;
    }

    public void setSet_return_no(String set_return_no) {
        this.set_return_no = set_return_no == null ? null : set_return_no.trim();
    }

    public String getState1() {
        return state1;
    }

    public void setState1(String state1) {
        this.state1 = state1 == null ? null : state1.trim();
    }

    public String getState2() {
        return state2;
    }

    public void setState2(String state2) {
        this.state2 = state2 == null ? null : state2.trim();
    }

    public BigDecimal getTmoney() {
        return tmoney;
    }

    public void setTmoney(BigDecimal tmoney) {
        this.tmoney = tmoney;
    }

    public BigDecimal getTnum() {
        return tnum;
    }

    public void setTnum(BigDecimal tnum) {
        this.tnum = tnum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public Long getUpoper() {
        return upoper;
    }

    public void setUpoper(Long upoper) {
        this.upoper = upoper;
    }

    public Long getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(Long logistics_id) {
        this.logistics_id = logistics_id;
    }

    public Long getOrderForm_id() {
        return orderForm_id;
    }

    public void setOrderForm_id(Long orderForm_id) {
        this.orderForm_id = orderForm_id;
    }

    public String getFinshtime() {
        return finshtime;
    }

    public void setFinshtime(String finshtime) {
        this.finshtime = finshtime == null ? null : finshtime.trim();
    }

    public Integer getOrder_status_store() {
        return order_status_store;
    }

    public void setOrder_status_store(Integer order_status_store) {
        this.order_status_store = order_status_store;
    }

    public BigDecimal getReturnTotal() {
        return returnTotal;
    }

    public void setReturnTotal(BigDecimal returnTotal) {
        this.returnTotal = returnTotal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUpoper_id() {
        return upoper_id;
    }

    public void setUpoper_id(Long upoper_id) {
        this.upoper_id = upoper_id;
    }
}