package com.sanbang.bean;

import java.util.Date;

public class ezs_classify {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String name;

    private String remarkValue;

    private String title;

    private Long re_id;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemarkValue() {
        return remarkValue;
    }

    public void setRemarkValue(String remarkValue) {
        this.remarkValue = remarkValue == null ? null : remarkValue.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getRe_id() {
        return re_id;
    }

    public void setRe_id(Long re_id) {
        this.re_id = re_id;
    }
}