package com.sanbang.bean;

import java.util.Date;

public class ezs_stock {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;
    //商品类型: 1.供应商商品，2.自营商品，3.样品商品
    private Integer goodClass;
    //ERP库存
    private Double iQuantity;
    //电商库存
    private Double mQuantity;
    //库存状态  1.释放，0.锁库
    private Integer status;
    //购买量
    private Double buyNum;

    private Long goodid;
    //订单编号
    private String orderNo;

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

    public Integer getGoodClass() {
        return goodClass;
    }

    public void setGoodClass(Integer goodClass) {
        this.goodClass = goodClass;
    }

    public Double getiQuantity() {
        return iQuantity;
    }

    public void setiQuantity(Double iQuantity) {
        this.iQuantity = iQuantity;
    }

    public Double getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(Double mQuantity) {
        this.mQuantity = mQuantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Double buyNum) {
        this.buyNum = buyNum;
    }

    public Long getGoodid() {
        return goodid;
    }

    public void setGoodid(Long goodid) {
        this.goodid = goodid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
}