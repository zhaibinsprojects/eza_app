package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_return_logistics {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Date add_cart_time;

    private String car_no;

    private Date end_time;

    private String logistics_name;

    private String logistics_no;

    private String msg;

    private String order_no;

    private String phone;

    private String proples;

    private String sendAddess;

    private Date service_time;

    private Integer status;

    private BigDecimal total_price;

    private Long setorder_id;

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

    public Date getAdd_cart_time() {
        return add_cart_time;
    }

    public void setAdd_cart_time(Date add_cart_time) {
        this.add_cart_time = add_cart_time;
    }

    public String getCar_no() {
        return car_no;
    }

    public void setCar_no(String car_no) {
        this.car_no = car_no == null ? null : car_no.trim();
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getLogistics_name() {
        return logistics_name;
    }

    public void setLogistics_name(String logistics_name) {
        this.logistics_name = logistics_name == null ? null : logistics_name.trim();
    }

    public String getLogistics_no() {
        return logistics_no;
    }

    public void setLogistics_no(String logistics_no) {
        this.logistics_no = logistics_no == null ? null : logistics_no.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getProples() {
        return proples;
    }

    public void setProples(String proples) {
        this.proples = proples == null ? null : proples.trim();
    }

    public String getSendAddess() {
        return sendAddess;
    }

    public void setSendAddess(String sendAddess) {
        this.sendAddess = sendAddess == null ? null : sendAddess.trim();
    }

    public Date getService_time() {
        return service_time;
    }

    public void setService_time(Date service_time) {
        this.service_time = service_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public Long getSetorder_id() {
        return setorder_id;
    }

    public void setSetorder_id(Long setorder_id) {
        this.setorder_id = setorder_id;
    }
}