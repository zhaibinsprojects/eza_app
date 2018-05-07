package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_storecart {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String cart_session_id;

    private Integer sc_status;

    private BigDecimal total_price;

    private Long store_id;

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

    public String getCart_session_id() {
        return cart_session_id;
    }

    public void setCart_session_id(String cart_session_id) {
        this.cart_session_id = cart_session_id == null ? null : cart_session_id.trim();
    }

    public Integer getSc_status() {
        return sc_status;
    }

    public void setSc_status(Integer sc_status) {
        this.sc_status = sc_status;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
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
}