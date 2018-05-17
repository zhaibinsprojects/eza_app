package com.sanbang.bean;

import java.util.Date;

public class ezs_customized_record {
    private Integer id;

    private Integer purchase_id;

    private Integer customized_id;

    private Date deal_time;

    private String remark;

    private Integer operate_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(Integer purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Integer getCustomized_id() {
        return customized_id;
    }

    public void setCustomized_id(Integer customized_id) {
        this.customized_id = customized_id;
    }

    public Date getDeal_time() {
        return deal_time;
    }

    public void setDeal_time(Date deal_time) {
        this.deal_time = deal_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getOperate_id() {
        return operate_id;
    }

    public void setOperate_id(Integer operate_id) {
        this.operate_id = operate_id;
    }
}