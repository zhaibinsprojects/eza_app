package com.sanbang.bean;

import java.util.Date;

public class ezs_album {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Boolean album_default;

    private String album_name;

    private Integer album_sequence;

    private Long album_cover_id;

    private Long user_id;

    private String alblum_info;

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

    public Boolean getAlbum_default() {
        return album_default;
    }

    public void setAlbum_default(Boolean album_default) {
        this.album_default = album_default;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name == null ? null : album_name.trim();
    }

    public Integer getAlbum_sequence() {
        return album_sequence;
    }

    public void setAlbum_sequence(Integer album_sequence) {
        this.album_sequence = album_sequence;
    }

    public Long getAlbum_cover_id() {
        return album_cover_id;
    }

    public void setAlbum_cover_id(Long album_cover_id) {
        this.album_cover_id = album_cover_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getAlblum_info() {
        return alblum_info;
    }

    public void setAlblum_info(String alblum_info) {
        this.alblum_info = alblum_info == null ? null : alblum_info.trim();
    }
}