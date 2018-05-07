package com.sanbang.bean;

import java.util.Date;

public class ezs_pact {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String order_no;

    private String pact;

    private Integer pact_mold;

    private Integer pact_type;

    private Date sign_time;

    private Integer status;

    private Long buyUser_id;

    private Long sellerUser_id;

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

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public String getPact() {
        return pact;
    }

    public void setPact(String pact) {
        this.pact = pact == null ? null : pact.trim();
    }

    public Integer getPact_mold() {
        return pact_mold;
    }

    public void setPact_mold(Integer pact_mold) {
        this.pact_mold = pact_mold;
    }

    public Integer getPact_type() {
        return pact_type;
    }

    public void setPact_type(Integer pact_type) {
        this.pact_type = pact_type;
    }

    public Date getSign_time() {
        return sign_time;
    }

    public void setSign_time(Date sign_time) {
        this.sign_time = sign_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getBuyUser_id() {
        return buyUser_id;
    }

    public void setBuyUser_id(Long buyUser_id) {
        this.buyUser_id = buyUser_id;
    }

    public Long getSellerUser_id() {
        return sellerUser_id;
    }

    public void setSellerUser_id(Long sellerUser_id) {
        this.sellerUser_id = sellerUser_id;
    }
}