package com.sanbang.bean;

import java.util.Date;

public class ezs_customized_res {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String customized_status;

    private String remark;

    private Long customized_id;

    private Long goods_id;

    private Long supplier_id;

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

    public String getCustomized_status() {
        return customized_status;
    }

    public void setCustomized_status(String customized_status) {
        this.customized_status = customized_status == null ? null : customized_status.trim();
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

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Long supplier_id) {
        this.supplier_id = supplier_id;
    }
}