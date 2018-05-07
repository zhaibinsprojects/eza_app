package com.sanbang.bean;

import java.util.Date;

public class ezs_bonus_record {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String bonusType;

    private String bonusMethod;

    private Double bonusMoney;

    private String bonusStandard;

    private String companyName;

    private Date updateTime;

    private Long bonusRecord_user_id;

    private Long bonusRule_id;

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

    public String getBonusType() {
        return bonusType;
    }

    public void setBonusType(String bonusType) {
        this.bonusType = bonusType == null ? null : bonusType.trim();
    }

    public String getBonusMethod() {
        return bonusMethod;
    }

    public void setBonusMethod(String bonusMethod) {
        this.bonusMethod = bonusMethod == null ? null : bonusMethod.trim();
    }

    public Double getBonusMoney() {
        return bonusMoney;
    }

    public void setBonusMoney(Double bonusMoney) {
        this.bonusMoney = bonusMoney;
    }

    public String getBonusStandard() {
        return bonusStandard;
    }

    public void setBonusStandard(String bonusStandard) {
        this.bonusStandard = bonusStandard == null ? null : bonusStandard.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getBonusRecord_user_id() {
        return bonusRecord_user_id;
    }

    public void setBonusRecord_user_id(Long bonusRecord_user_id) {
        this.bonusRecord_user_id = bonusRecord_user_id;
    }

    public Long getBonusRule_id() {
        return bonusRule_id;
    }

    public void setBonusRule_id(Long bonusRule_id) {
        this.bonusRule_id = bonusRule_id;
    }
}