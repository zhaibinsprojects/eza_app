package com.sanbang.bean;

import java.util.Date;

public class ezs_goods_audit_log {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Integer actionId;

    private Long goodsAuditId;

    private String logRemarks;

    private String remarks;

    private Integer status;

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

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Long getGoodsAuditId() {
        return goodsAuditId;
    }

    public void setGoodsAuditId(Long goodsAuditId) {
        this.goodsAuditId = goodsAuditId;
    }

    public String getLogRemarks() {
        return logRemarks;
    }

    public void setLogRemarks(String logRemarks) {
        this.logRemarks = logRemarks == null ? null : logRemarks.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}