package com.sanbang.bean;

import java.util.Date;

public class ezs_adv_pos {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String ap_acc_url;

    private Integer ap_height;

    private Integer ap_price;

    private Integer ap_sale_type;

    private Integer ap_show_type;

    private Integer ap_status;

    private Integer ap_sys_type;

    private String ap_text;

    private String ap_title;

    private String ap_type;

    private Integer ap_use_status;

    private Integer ap_width;

    private Long ap_acc_id;

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

    public String getAp_acc_url() {
        return ap_acc_url;
    }

    public void setAp_acc_url(String ap_acc_url) {
        this.ap_acc_url = ap_acc_url == null ? null : ap_acc_url.trim();
    }

    public Integer getAp_height() {
        return ap_height;
    }

    public void setAp_height(Integer ap_height) {
        this.ap_height = ap_height;
    }

    public Integer getAp_price() {
        return ap_price;
    }

    public void setAp_price(Integer ap_price) {
        this.ap_price = ap_price;
    }

    public Integer getAp_sale_type() {
        return ap_sale_type;
    }

    public void setAp_sale_type(Integer ap_sale_type) {
        this.ap_sale_type = ap_sale_type;
    }

    public Integer getAp_show_type() {
        return ap_show_type;
    }

    public void setAp_show_type(Integer ap_show_type) {
        this.ap_show_type = ap_show_type;
    }

    public Integer getAp_status() {
        return ap_status;
    }

    public void setAp_status(Integer ap_status) {
        this.ap_status = ap_status;
    }

    public Integer getAp_sys_type() {
        return ap_sys_type;
    }

    public void setAp_sys_type(Integer ap_sys_type) {
        this.ap_sys_type = ap_sys_type;
    }

    public String getAp_text() {
        return ap_text;
    }

    public void setAp_text(String ap_text) {
        this.ap_text = ap_text == null ? null : ap_text.trim();
    }

    public String getAp_title() {
        return ap_title;
    }

    public void setAp_title(String ap_title) {
        this.ap_title = ap_title == null ? null : ap_title.trim();
    }

    public String getAp_type() {
        return ap_type;
    }

    public void setAp_type(String ap_type) {
        this.ap_type = ap_type == null ? null : ap_type.trim();
    }

    public Integer getAp_use_status() {
        return ap_use_status;
    }

    public void setAp_use_status(Integer ap_use_status) {
        this.ap_use_status = ap_use_status;
    }

    public Integer getAp_width() {
        return ap_width;
    }

    public void setAp_width(Integer ap_width) {
        this.ap_width = ap_width;
    }

    public Long getAp_acc_id() {
        return ap_acc_id;
    }

    public void setAp_acc_id(Long ap_acc_id) {
        this.ap_acc_id = ap_acc_id;
    }
}