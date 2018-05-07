package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_readjust {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Integer approve;

    private Date approve_time;

    private String msg;

    private BigDecimal price;

    private Long applyuser_id;

    private Long cause_id;

    private Long orderForm_id;

    private Long proof_id;

    private Long user_id;

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

    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    public Date getApprove_time() {
        return approve_time;
    }

    public void setApprove_time(Date approve_time) {
        this.approve_time = approve_time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getApplyuser_id() {
        return applyuser_id;
    }

    public void setApplyuser_id(Long applyuser_id) {
        this.applyuser_id = applyuser_id;
    }

    public Long getCause_id() {
        return cause_id;
    }

    public void setCause_id(Long cause_id) {
        this.cause_id = cause_id;
    }

    public Long getOrderForm_id() {
        return orderForm_id;
    }

    public void setOrderForm_id(Long orderForm_id) {
        this.orderForm_id = orderForm_id;
    }

    public Long getProof_id() {
        return proof_id;
    }

    public void setProof_id(Long proof_id) {
        this.proof_id = proof_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}