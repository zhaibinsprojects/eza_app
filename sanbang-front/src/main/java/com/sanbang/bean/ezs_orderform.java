package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_orderform {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Integer adjust_price;

    private BigDecimal all_price;

    private String cart_session_id;

    private BigDecimal end_price;

    private Date estimateTime;

    private Date finishtime;

    private BigDecimal first_price;

    private BigDecimal goods_amount;

    private String order_no;

    private Integer order_status;

    private String order_type;

    private Integer pact_status;

    private Integer pay_mode;

    private Integer pay_mode01;

    private Integer pay_mode02;

    private Integer sc_status;

    private BigDecimal total_price;

    private Long address_id;

    private Long user_id;

    private Long weAddress_id;

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

    public Integer getAdjust_price() {
        return adjust_price;
    }

    public void setAdjust_price(Integer adjust_price) {
        this.adjust_price = adjust_price;
    }

    public BigDecimal getAll_price() {
        return all_price;
    }

    public void setAll_price(BigDecimal all_price) {
        this.all_price = all_price;
    }

    public String getCart_session_id() {
        return cart_session_id;
    }

    public void setCart_session_id(String cart_session_id) {
        this.cart_session_id = cart_session_id == null ? null : cart_session_id.trim();
    }

    public BigDecimal getEnd_price() {
        return end_price;
    }

    public void setEnd_price(BigDecimal end_price) {
        this.end_price = end_price;
    }

    public Date getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(Date estimateTime) {
        this.estimateTime = estimateTime;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public BigDecimal getFirst_price() {
        return first_price;
    }

    public void setFirst_price(BigDecimal first_price) {
        this.first_price = first_price;
    }

    public BigDecimal getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(BigDecimal goods_amount) {
        this.goods_amount = goods_amount;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public Integer getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Integer order_status) {
        this.order_status = order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type == null ? null : order_type.trim();
    }

    public Integer getPact_status() {
        return pact_status;
    }

    public void setPact_status(Integer pact_status) {
        this.pact_status = pact_status;
    }

    public Integer getPay_mode() {
        return pay_mode;
    }

    public void setPay_mode(Integer pay_mode) {
        this.pay_mode = pay_mode;
    }

    public Integer getPay_mode01() {
        return pay_mode01;
    }

    public void setPay_mode01(Integer pay_mode01) {
        this.pay_mode01 = pay_mode01;
    }

    public Integer getPay_mode02() {
        return pay_mode02;
    }

    public void setPay_mode02(Integer pay_mode02) {
        this.pay_mode02 = pay_mode02;
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

    public Long getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Long address_id) {
        this.address_id = address_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getWeAddress_id() {
        return weAddress_id;
    }

    public void setWeAddress_id(Long weAddress_id) {
        this.weAddress_id = weAddress_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}