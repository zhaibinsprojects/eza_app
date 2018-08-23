package com.sanbang.bean;

import java.util.Date;

public class ezs_price_trend_xl {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String content;

    private Date data_time;

    private Double price;

    private String priceAttribute;

    private String purpose;

    private String source;

    private Long goodClass_id;

    private Long region_id;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getData_time() {
        return data_time;
    }

    public void setData_time(Date data_time) {
        this.data_time = data_time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPriceAttribute() {
        return priceAttribute;
    }

    public void setPriceAttribute(String priceAttribute) {
        this.priceAttribute = priceAttribute == null ? null : priceAttribute.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Long getGoodClass_id() {
        return goodClass_id;
    }

    public void setGoodClass_id(Long goodClass_id) {
        this.goodClass_id = goodClass_id;
    }

    public Long getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }
}