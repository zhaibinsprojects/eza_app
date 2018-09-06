package com.sanbang.bean;

import java.util.Date;

public class ezs_customized_res {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String attr1;

    private String attr2;

    private String attr3;

    private String customized_status;

    private String remark;

    private Date updateDate;

    private Long customized_id;

    private Long goods_id;

    private Long supplier_id;

    private Long updateUser_id;

    private Long storeId;

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

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1 == null ? null : attr1.trim();
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3 == null ? null : attr3.trim();
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

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public Long getUpdateUser_id() {
        return updateUser_id;
    }

    public void setUpdateUser_id(Long updateUser_id) {
        this.updateUser_id = updateUser_id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}