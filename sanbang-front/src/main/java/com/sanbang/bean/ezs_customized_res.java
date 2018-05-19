package com.sanbang.bean;

import java.util.Date;

public class ezs_customized_res {
    private Integer id;

    private Integer supplier_id;

    private Integer customized_id;

    private String remark;

    private String customized_status;

    private Integer goods_id;

    private Date addTime;

    private Boolean deleteStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public Integer getCustomized_id() {
        return customized_id;
    }

    public void setCustomized_id(Integer customized_id) {
        this.customized_id = customized_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCustomized_status() {
        return customized_status;
    }

    public void setCustomized_status(String customized_status) {
        this.customized_status = customized_status == null ? null : customized_status.trim();
    }

    public Integer getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
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
}