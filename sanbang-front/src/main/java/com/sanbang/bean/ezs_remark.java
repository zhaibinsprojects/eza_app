package com.sanbang.bean;

import java.util.Date;

public class ezs_remark {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String name;

    private String title;

    private Long substance_id;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getSubstance_id() {
        return substance_id;
    }

    public void setSubstance_id(Long substance_id) {
        this.substance_id = substance_id;
    }
}