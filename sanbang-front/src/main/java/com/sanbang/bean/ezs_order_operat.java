package com.sanbang.bean;

import java.util.Date;

public class ezs_order_operat {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Long operatStatus_id;

    private Long orderForm_id;

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

    public Long getOperatStatus_id() {
        return operatStatus_id;
    }

    public void setOperatStatus_id(Long operatStatus_id) {
        this.operatStatus_id = operatStatus_id;
    }

    public Long getOrderForm_id() {
        return orderForm_id;
    }

    public void setOrderForm_id(Long orderForm_id) {
        this.orderForm_id = orderForm_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}