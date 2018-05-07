package com.sanbang.bean;

import java.util.Date;

public class ezs_specialsubject {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String abbreviation;

    private String colTemplate;

    private String description;

    private String keyWord;

    private String name;

    private Integer orderid;

    private String pcContentPhoto;

    private String pcTitlePhoto;

    private String phoTemplate;

    private String phoneContentPhoto;

    private String phoneTitlePhoto;

    private Boolean recommend;

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation == null ? null : abbreviation.trim();
    }

    public String getColTemplate() {
        return colTemplate;
    }

    public void setColTemplate(String colTemplate) {
        this.colTemplate = colTemplate == null ? null : colTemplate.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public String getPcContentPhoto() {
        return pcContentPhoto;
    }

    public void setPcContentPhoto(String pcContentPhoto) {
        this.pcContentPhoto = pcContentPhoto == null ? null : pcContentPhoto.trim();
    }

    public String getPcTitlePhoto() {
        return pcTitlePhoto;
    }

    public void setPcTitlePhoto(String pcTitlePhoto) {
        this.pcTitlePhoto = pcTitlePhoto == null ? null : pcTitlePhoto.trim();
    }

    public String getPhoTemplate() {
        return phoTemplate;
    }

    public void setPhoTemplate(String phoTemplate) {
        this.phoTemplate = phoTemplate == null ? null : phoTemplate.trim();
    }

    public String getPhoneContentPhoto() {
        return phoneContentPhoto;
    }

    public void setPhoneContentPhoto(String phoneContentPhoto) {
        this.phoneContentPhoto = phoneContentPhoto == null ? null : phoneContentPhoto.trim();
    }

    public String getPhoneTitlePhoto() {
        return phoneTitlePhoto;
    }

    public void setPhoneTitlePhoto(String phoneTitlePhoto) {
        this.phoneTitlePhoto = phoneTitlePhoto == null ? null : phoneTitlePhoto.trim();
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }
}