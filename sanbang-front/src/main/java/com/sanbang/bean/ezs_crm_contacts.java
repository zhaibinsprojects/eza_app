package com.sanbang.bean;

import java.util.Date;

public class ezs_crm_contacts {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String infor_source;

    private String address;

    private String department;

    private String email;

    private String mobile;

    private String post;

    private String sex;

    private String telephone;

    private String tn_id;

    private String truename;

    private Long consumer_id;

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

    public String getInfor_source() {
        return infor_source;
    }

    public void setInfor_source(String infor_source) {
        this.infor_source = infor_source == null ? null : infor_source.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getTn_id() {
        return tn_id;
    }

    public void setTn_id(String tn_id) {
        this.tn_id = tn_id == null ? null : tn_id.trim();
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename == null ? null : truename.trim();
    }

    public Long getConsumer_id() {
        return consumer_id;
    }

    public void setConsumer_id(Long consumer_id) {
        this.consumer_id = consumer_id;
    }
}