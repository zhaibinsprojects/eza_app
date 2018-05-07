package com.sanbang.bean;

import java.util.Date;

public class ezs_introduce_code {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String l_link;

    private String s_link;

    private String codeURL;

    private String description;

    private String introduceType;

    private Long benefitUser_id;

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

    public String getL_link() {
        return l_link;
    }

    public void setL_link(String l_link) {
        this.l_link = l_link == null ? null : l_link.trim();
    }

    public String getS_link() {
        return s_link;
    }

    public void setS_link(String s_link) {
        this.s_link = s_link == null ? null : s_link.trim();
    }

    public String getCodeURL() {
        return codeURL;
    }

    public void setCodeURL(String codeURL) {
        this.codeURL = codeURL == null ? null : codeURL.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIntroduceType() {
        return introduceType;
    }

    public void setIntroduceType(String introduceType) {
        this.introduceType = introduceType == null ? null : introduceType.trim();
    }

    public Long getBenefitUser_id() {
        return benefitUser_id;
    }

    public void setBenefitUser_id(Long benefitUser_id) {
        this.benefitUser_id = benefitUser_id;
    }
}