package com.sanbang.bean;

import java.util.Date;

public class ezs_advert {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Date ad_begin_time;

    private Integer ad_click_num;

    private Date ad_end_time;

    private Integer ad_gold;

    private Integer ad_slide_sequence;

    private Integer ad_status;

    private String ad_text;

    private String ad_title;

    private String ad_url;

    private Long ad_acc_id;

    private Long ad_ap_id;

    private Long ad_user_id;

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

    public Date getAd_begin_time() {
        return ad_begin_time;
    }

    public void setAd_begin_time(Date ad_begin_time) {
        this.ad_begin_time = ad_begin_time;
    }

    public Integer getAd_click_num() {
        return ad_click_num;
    }

    public void setAd_click_num(Integer ad_click_num) {
        this.ad_click_num = ad_click_num;
    }

    public Date getAd_end_time() {
        return ad_end_time;
    }

    public void setAd_end_time(Date ad_end_time) {
        this.ad_end_time = ad_end_time;
    }

    public Integer getAd_gold() {
        return ad_gold;
    }

    public void setAd_gold(Integer ad_gold) {
        this.ad_gold = ad_gold;
    }

    public Integer getAd_slide_sequence() {
        return ad_slide_sequence;
    }

    public void setAd_slide_sequence(Integer ad_slide_sequence) {
        this.ad_slide_sequence = ad_slide_sequence;
    }

    public Integer getAd_status() {
        return ad_status;
    }

    public void setAd_status(Integer ad_status) {
        this.ad_status = ad_status;
    }

    public String getAd_text() {
        return ad_text;
    }

    public void setAd_text(String ad_text) {
        this.ad_text = ad_text == null ? null : ad_text.trim();
    }

    public String getAd_title() {
        return ad_title;
    }

    public void setAd_title(String ad_title) {
        this.ad_title = ad_title == null ? null : ad_title.trim();
    }

    public String getAd_url() {
        return ad_url;
    }

    public void setAd_url(String ad_url) {
        this.ad_url = ad_url == null ? null : ad_url.trim();
    }

    public Long getAd_acc_id() {
        return ad_acc_id;
    }

    public void setAd_acc_id(Long ad_acc_id) {
        this.ad_acc_id = ad_acc_id;
    }

    public Long getAd_ap_id() {
        return ad_ap_id;
    }

    public void setAd_ap_id(Long ad_ap_id) {
        this.ad_ap_id = ad_ap_id;
    }

    public Long getAd_user_id() {
        return ad_user_id;
    }

    public void setAd_user_id(Long ad_user_id) {
        this.ad_user_id = ad_user_id;
    }
}