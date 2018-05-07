package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_set_return_order {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private BigDecimal account_receivable;

    private String applyReturn_reason;

    private Date paid_time;

    private BigDecimal real_received;

    private BigDecimal real_refund_account;

    private String set_return_no;

    private String state1;

    private String state2;

    private BigDecimal tmoney;

    private BigDecimal tnum;

    private Long logistics_id;

    private Long orderForm_id;

    private String bank;

    private String bankCode;

    private String msg;

    private Long bill_id;

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

    public BigDecimal getAccount_receivable() {
        return account_receivable;
    }

    public void setAccount_receivable(BigDecimal account_receivable) {
        this.account_receivable = account_receivable;
    }

    public String getApplyReturn_reason() {
        return applyReturn_reason;
    }

    public void setApplyReturn_reason(String applyReturn_reason) {
        this.applyReturn_reason = applyReturn_reason == null ? null : applyReturn_reason.trim();
    }

    public Date getPaid_time() {
        return paid_time;
    }

    public void setPaid_time(Date paid_time) {
        this.paid_time = paid_time;
    }

    public BigDecimal getReal_received() {
        return real_received;
    }

    public void setReal_received(BigDecimal real_received) {
        this.real_received = real_received;
    }

    public BigDecimal getReal_refund_account() {
        return real_refund_account;
    }

    public void setReal_refund_account(BigDecimal real_refund_account) {
        this.real_refund_account = real_refund_account;
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Long getBill_id() {
        return bill_id;
    }

    public void setBill_id(Long bill_id) {
        this.bill_id = bill_id;
    }
}