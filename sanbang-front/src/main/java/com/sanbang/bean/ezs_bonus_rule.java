package com.sanbang.bean;

import java.util.Date;

public class ezs_bonus_rule {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String bonusMethod;

    private String bonusRuleType;

    private Double fixedMoney;

    private Double maxMoney;

    private Double minMoney;

    private Double percentage;

    private String unit;

    private Date updateTime;

    private Long creator_id;

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

    public String getBonusMethod() {
        return bonusMethod;
    }

    public void setBonusMethod(String bonusMethod) {
        this.bonusMethod = bonusMethod == null ? null : bonusMethod.trim();
    }

    public String getBonusRuleType() {
        return bonusRuleType;
    }

    public void setBonusRuleType(String bonusRuleType) {
        this.bonusRuleType = bonusRuleType == null ? null : bonusRuleType.trim();
    }

    public Double getFixedMoney() {
        return fixedMoney;
    }

    public void setFixedMoney(Double fixedMoney) {
        this.fixedMoney = fixedMoney;
    }

    public Double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Double minMoney) {
        this.minMoney = minMoney;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(Long creator_id) {
        this.creator_id = creator_id;
    }
}