package com.sanbang.bean;

import java.util.Date;

public class ezs_communicate {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String content;

    private Boolean display;

    private String name;

    private Long dictType_id;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getDictType_id() {
        return dictType_id;
    }

    public void setDictType_id(Long dictType_id) {
        this.dictType_id = dictType_id;
    }
}