package com.sanbang.bean;

import java.util.Date;

public class ezs_price_trend_every {
    private Long id;

    private Long price_trend_id;

    private String price;

    private Date addTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice_trend_id() {
        return price_trend_id;
    }

    public void setPrice_trend_id(Long price_trend_id) {
        this.price_trend_id = price_trend_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}