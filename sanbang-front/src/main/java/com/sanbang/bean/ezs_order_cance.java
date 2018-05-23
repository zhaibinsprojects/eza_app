package com.sanbang.bean;

import java.util.Date;

public class ezs_order_cance {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;
    // 发起方 (客户， 平台)
    private String appscope;
    // 描述
    private String msg;
    //取消原因
    private String name;
    // 订单编号
    private String order_no;
    // 发起方 (客户， 平台)
    private Long user_id;

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

    public String getAppscope() {
        return appscope;
    }

    public void setAppscope(String appscope) {
        this.appscope = appscope == null ? null : appscope.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}