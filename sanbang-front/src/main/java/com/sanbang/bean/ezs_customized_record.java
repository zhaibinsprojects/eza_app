package com.sanbang.bean;

import java.util.Date;

public class ezs_customized_record {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Date deal_time;

    private String flag;

    private Boolean flagMsg;

    private String remark;

    private Long customized_id;

    private Long operater_id;

    private Long purchaser_id;

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

    public Date getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(Date deal_time) {
        this.deal_time = deal_time;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Boolean getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(Boolean flagMsg) {
        this.flagMsg = flagMsg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCustomized_id() {
        return customized_id;
    }

    public void setCustomized_id(Long customized_id) {
        this.customized_id = customized_id;
    }

    public Long getOperater_id() {
        return operater_id;
    }

    public void setOperater_id(Long operater_id) {
        this.operater_id = operater_id;
    }

    public Long getPurchaser_id() {
        return purchaser_id;
    }

    public void setPurchaser_id(Long purchaser_id) {
        this.purchaser_id = purchaser_id;
    }
}