package com.sanbang.bean;

import java.util.Date;

public class ezs_clean_log {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String clean_condition;

    private Integer clean_sample;

    private String clean_type;

    private Integer init_sample;

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

    public String getClean_condition() {
        return clean_condition;
    }

    public void setClean_condition(String clean_condition) {
        this.clean_condition = clean_condition == null ? null : clean_condition.trim();
    }

    public Integer getClean_sample() {
        return clean_sample;
    }

    public void setClean_sample(Integer clean_sample) {
        this.clean_sample = clean_sample;
    }

    public String getClean_type() {
        return clean_type;
    }

    public void setClean_type(String clean_type) {
        this.clean_type = clean_type == null ? null : clean_type.trim();
    }

    public Integer getInit_sample() {
        return init_sample;
    }

    public void setInit_sample(Integer init_sample) {
        this.init_sample = init_sample;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}