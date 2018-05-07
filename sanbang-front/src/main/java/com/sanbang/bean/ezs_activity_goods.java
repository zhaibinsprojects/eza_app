package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_activity_goods {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private BigDecimal ag_price;

    private Integer ag_status;

    private Double saleAmount;

    private Double totalAmount;

    private Long act_id;

    private Long ag_admin_id;

    private Long ag_goods_id;

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

    public BigDecimal getAg_price() {
        return ag_price;
    }

    public void setAg_price(BigDecimal ag_price) {
        this.ag_price = ag_price;
    }

    public Integer getAg_status() {
        return ag_status;
    }

    public void setAg_status(Integer ag_status) {
        this.ag_status = ag_status;
    }

    public Double getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(Double saleAmount) {
        this.saleAmount = saleAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getAct_id() {
        return act_id;
    }

    public void setAct_id(Long act_id) {
        this.act_id = act_id;
    }

    public Long getAg_admin_id() {
        return ag_admin_id;
    }

    public void setAg_admin_id(Long ag_admin_id) {
        this.ag_admin_id = ag_admin_id;
    }

    public Long getAg_goods_id() {
        return ag_goods_id;
    }

    public void setAg_goods_id(Long ag_goods_id) {
        this.ag_goods_id = ag_goods_id;
    }
}