package com.sanbang.bean;

import java.util.Date;

public class ezs_documentshare {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Integer give;

    private Integer house;

    private Long ezsSubstance_id;

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

    public Integer getGive() {
        return give;
    }

    public void setGive(Integer give) {
        this.give = give;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public Long getEzsSubstance_id() {
        return ezsSubstance_id;
    }

    public void setEzsSubstance_id(Long ezsSubstance_id) {
        this.ezsSubstance_id = ezsSubstance_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}