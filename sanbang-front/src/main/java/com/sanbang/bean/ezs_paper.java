package com.sanbang.bean;

import java.util.Date;

public class ezs_paper {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String paperType;

    private Date validDate;

    private Long certificate_id;

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

    public String getPaperType() {
        return paperType;
    }

    public void setPaperType(String paperType) {
        this.paperType = paperType == null ? null : paperType.trim();
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Long getCertificate_id() {
        return certificate_id;
    }

    public void setCertificate_id(Long certificate_id) {
        this.certificate_id = certificate_id;
    }
}