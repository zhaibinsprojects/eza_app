package com.sanbang.bean;

import java.util.Date;

public class ezs_column {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String accessPath;

    private Integer attribute;

    private Integer browsePower;

    private String colTemplate;

    private String description;

    private Integer examine;

    private String keyWord;

    private String name;

    private Integer orderid;

    private String phoTemplate;

    private Boolean reveal;

    private Boolean review;

    private String title;

    private Boolean window;

    private Long gc_id;

    private Long user_id;

    private Integer columnLevel;

    private String content;

    private String prepareColumn;

    private Long parentEzsColumn_id;

    private String finalSection;

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

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath == null ? null : accessPath.trim();
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getBrowsePower() {
        return browsePower;
    }

    public void setBrowsePower(Integer browsePower) {
        this.browsePower = browsePower;
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

    public Integer getExamine() {
        return examine;
    }

    public void setExamine(Integer examine) {
        this.examine = examine;
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

    public String getPhoTemplate() {
        return phoTemplate;
    }

    public void setPhoTemplate(String phoTemplate) {
        this.phoTemplate = phoTemplate == null ? null : phoTemplate.trim();
    }

    public Boolean getReveal() {
        return reveal;
    }

    public void setReveal(Boolean reveal) {
        this.reveal = reveal;
    }

    public Boolean getReview() {
        return review;
    }

    public void setReview(Boolean review) {
        this.review = review;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Boolean getWindow() {
        return window;
    }

    public void setWindow(Boolean window) {
        this.window = window;
    }

    public Long getGc_id() {
        return gc_id;
    }

    public void setGc_id(Long gc_id) {
        this.gc_id = gc_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getColumnLevel() {
        return columnLevel;
    }

    public void setColumnLevel(Integer columnLevel) {
        this.columnLevel = columnLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPrepareColumn() {
        return prepareColumn;
    }

    public void setPrepareColumn(String prepareColumn) {
        this.prepareColumn = prepareColumn == null ? null : prepareColumn.trim();
    }

    public Long getParentEzsColumn_id() {
        return parentEzsColumn_id;
    }

    public void setParentEzsColumn_id(Long parentEzsColumn_id) {
        this.parentEzsColumn_id = parentEzsColumn_id;
    }

    public String getFinalSection() {
        return finalSection;
    }

    public void setFinalSection(String finalSection) {
        this.finalSection = finalSection == null ? null : finalSection.trim();
    }
}