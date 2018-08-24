package com.sanbang.bean;

import java.util.Date;

public class ezs_buy_log {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String buy_user;

    private String buy_way;

    private Double price;

    private Long priceService_id;

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

    public String getBuy_user() {
        return buy_user;
    }

    public void setBuy_user(String buy_user) {
        this.buy_user = buy_user == null ? null : buy_user.trim();
    }

    public String getBuy_way() {
        return buy_way;
    }

    public void setBuy_way(String buy_way) {
        this.buy_way = buy_way == null ? null : buy_way.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getPriceService_id() {
        return priceService_id;
    }

    public void setPriceService_id(Long priceService_id) {
        this.priceService_id = priceService_id;
    }
}