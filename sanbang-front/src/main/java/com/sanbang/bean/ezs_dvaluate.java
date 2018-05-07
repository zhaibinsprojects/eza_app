package com.sanbang.bean;

import java.util.Date;

public class ezs_dvaluate {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String conttent;

    private Double goodQuality;

    private Double logistics;

    private String order_no;

    private Double serviceQuality;

    private Long goods_id;

    private byte[] user;

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

    public String getConttent() {
        return conttent;
    }

    public void setConttent(String conttent) {
        this.conttent = conttent == null ? null : conttent.trim();
    }

    public Double getGoodQuality() {
        return goodQuality;
    }

    public void setGoodQuality(Double goodQuality) {
        this.goodQuality = goodQuality;
    }

    public Double getLogistics() {
        return logistics;
    }

    public void setLogistics(Double logistics) {
        this.logistics = logistics;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public Double getServiceQuality() {
        return serviceQuality;
    }

    public void setServiceQuality(Double serviceQuality) {
        this.serviceQuality = serviceQuality;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public byte[] getUser() {
        return user;
    }

    public void setUser(byte[] user) {
        this.user = user;
    }
}