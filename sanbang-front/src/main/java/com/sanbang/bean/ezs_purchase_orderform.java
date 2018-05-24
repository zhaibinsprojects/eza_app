package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_purchase_orderform {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private BigDecimal all_price;

    private String cart_session_id;

    private BigDecimal end_price;

    private Date end_time;

    private Date finishtime;

    private Date firest_time;

    private BigDecimal first_price;

    private Long goodsId;

    private BigDecimal goods_amount;

    private String order_no;

    private Integer order_status;

    private String order_type;

    private String pact_no;

    private Integer pact_status;

    private Integer pay_mode;

    private Integer sc_status;

    private BigDecimal total_price;

    private Long address_id;

    private Long bill_id;

    private Long buyBiil_id;

    private Long invoice_id;

    private Long logistics_id;

    private Long user_id;

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

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public Date getFirest_time() {
        return firest_time;
    }

    public void setFirest_time(Date firest_time) {
        this.firest_time = firest_time;
    }

    public BigDecimal getFirst_price() {
        return first_price;
    }

    public void setFirst_price(BigDecimal first_price) {
        this.first_price = first_price;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    public String getPact_no() {
        return pact_no;
    }

    public void setPact_no(String pact_no) {
        this.pact_no = pact_no == null ? null : pact_no.trim();
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

    public Long getBill_id() {
        return bill_id;
    }

    public void setBill_id(Long bill_id) {
        this.bill_id = bill_id;
    }

    public Long getBuyBiil_id() {
        return buyBiil_id;
    }

    public void setBuyBiil_id(Long buyBiil_id) {
        this.buyBiil_id = buyBiil_id;
    }

    public Long getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(Long invoice_id) {
        this.invoice_id = invoice_id;
    }

    public Long getLogistics_id() {
        return logistics_id;
    }

    public void setLogistics_id(Long logistics_id) {
        this.logistics_id = logistics_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}