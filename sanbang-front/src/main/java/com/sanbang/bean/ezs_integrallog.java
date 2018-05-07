package com.sanbang.bean;

import java.util.Date;

public class ezs_integrallog {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Integer integral;

    private String type;

    private Long integral_user_id;

    private Long operate_user_id;

    private String content;

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

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Long getIntegral_user_id() {
        return integral_user_id;
    }

    public void setIntegral_user_id(Long integral_user_id) {
        this.integral_user_id = integral_user_id;
    }

    public Long getOperate_user_id() {
        return operate_user_id;
    }

    public void setOperate_user_id(Long operate_user_id) {
        this.operate_user_id = operate_user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}