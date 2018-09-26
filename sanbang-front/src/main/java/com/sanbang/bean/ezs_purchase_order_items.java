package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_purchase_order_items {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Date arriveDate;

    private String cUnitID;

    private BigDecimal goods_amount;

    private BigDecimal goods_price;

    private String order_no;

    private BigDecimal total_price;

    private Long goods_id;

    private Long util_id;

    private String msg;

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

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getcUnitID() {
        return cUnitID;
    }

    public void setcUnitID(String cUnitID) {
        this.cUnitID = cUnitID == null ? null : cUnitID.trim();
    }

    public BigDecimal getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(BigDecimal goods_amount) {
        this.goods_amount = goods_amount;
    }

    public BigDecimal getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(BigDecimal goods_price) {
        this.goods_price = goods_price;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getUtil_id() {
        return util_id;
    }

    public void setUtil_id(Long util_id) {
        this.util_id = util_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}