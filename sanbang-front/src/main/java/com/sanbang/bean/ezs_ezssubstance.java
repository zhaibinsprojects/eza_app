package com.sanbang.bean;

import java.util.Date;

public class ezs_ezssubstance {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String attachment;

    private Integer attribute;

    private String author;

    private Boolean bold;

    private Integer clickLike;

    private Integer clickRate;

    private Integer clickRrample;

    private String content;

    private String fixedTime;

    private String keyWord;

    private Boolean linkPort;

    private String linkUrl;

    private String meta;

    private String multimediaFiles;

    private String name;

    private String origin;

    private String originUrl;

    private Boolean pcView;

    private String photos;

    private Date publicTime;

    private String remarkValue;

    private Integer staticStatus;

    private Integer status;

    private String subheading;

    private String tagTitle;

    private String thumbnail;

    private String titleColor;

    private Long childEc_id;

    private Long ec_id;

    private Long ss_id;

    private Long u_id;

    private Long user_id;
    //add by zhaibin 获取其上级栏目
    private ezs_column parentColumn;

    public ezs_column getParentColumn() {
		return parentColumn;
	}

	public void setParentColumn(ezs_column parentColumn) {
		this.parentColumn = parentColumn;
	}

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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Integer getClickLike() {
        return clickLike;
    }

    public void setClickLike(Integer clickLike) {
        this.clickLike = clickLike;
    }

    public Integer getClickRate() {
        return clickRate;
    }

    public void setClickRate(Integer clickRate) {
        this.clickRate = clickRate;
    }

    public Integer getClickRrample() {
        return clickRrample;
    }

    public void setClickRrample(Integer clickRrample) {
        this.clickRrample = clickRrample;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFixedTime() {
        return fixedTime;
    }

    public void setFixedTime(String fixedTime) {
        this.fixedTime = fixedTime == null ? null : fixedTime.trim();
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord == null ? null : keyWord.trim();
    }

    public Boolean getLinkPort() {
        return linkPort;
    }

    public void setLinkPort(Boolean linkPort) {
        this.linkPort = linkPort;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl == null ? null : linkUrl.trim();
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta == null ? null : meta.trim();
    }

    public String getMultimediaFiles() {
        return multimediaFiles;
    }

    public void setMultimediaFiles(String multimediaFiles) {
        this.multimediaFiles = multimediaFiles == null ? null : multimediaFiles.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl == null ? null : originUrl.trim();
    }

    public Boolean getPcView() {
        return pcView;
    }

    public void setPcView(Boolean pcView) {
        this.pcView = pcView;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos == null ? null : photos.trim();
    }

    public Date getPublicTime() {
        return publicTime;
    }

    public void setPublicTime(Date publicTime) {
        this.publicTime = publicTime;
    }

    public String getRemarkValue() {
        return remarkValue;
    }

    public void setRemarkValue(String remarkValue) {
        this.remarkValue = remarkValue == null ? null : remarkValue.trim();
    }

    public Integer getStaticStatus() {
        return staticStatus;
    }

    public void setStaticStatus(Integer staticStatus) {
        this.staticStatus = staticStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSubheading() {
        return subheading;
    }

    public void setSubheading(String subheading) {
        this.subheading = subheading == null ? null : subheading.trim();
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle == null ? null : tagTitle.trim();
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail == null ? null : thumbnail.trim();
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor == null ? null : titleColor.trim();
    }

    public Long getChildEc_id() {
        return childEc_id;
    }

    public void setChildEc_id(Long childEc_id) {
        this.childEc_id = childEc_id;
    }

    public Long getEc_id() {
        return ec_id;
    }

    public void setEc_id(Long ec_id) {
        this.ec_id = ec_id;
    }

    public Long getSs_id() {
        return ss_id;
    }

    public void setSs_id(Long ss_id) {
        this.ss_id = ss_id;
    }

    public Long getU_id() {
        return u_id;
    }

    public void setU_id(Long u_id) {
        this.u_id = u_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}