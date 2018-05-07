package com.sanbang.bean;

import java.util.Date;

public class ezs_set_return_order_operat {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String operat;

    private Long setReturnOrder_id;

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

    public String getOperat() {
        return operat;
    }

    public void setOperat(String operat) {
        this.operat = operat == null ? null : operat.trim();
    }

    public Long getSetReturnOrder_id() {
        return setReturnOrder_id;
    }

    public void setSetReturnOrder_id(Long setReturnOrder_id) {
        this.setReturnOrder_id = setReturnOrder_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}