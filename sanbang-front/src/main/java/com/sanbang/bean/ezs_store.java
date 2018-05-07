package com.sanbang.bean;

import java.util.Date;

public class ezs_store {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String address;

    private Double assets;

    private String companyName;

    private Double covered;

    private Integer device_num;

    private Integer employee_num;

    private Double fixed_assets;

    private String location_detail;

    private Integer obtainYear;

    private String openBankNo;

    private String open_bank_name;

    private String open_branch_name;

    private String open_branch_no;

    private Date registerDate;

    private Boolean rent;

    private Integer status;

    private String userType;

    private Double yTurnover;

    private Long area_id;

    private Long cardType_id;

    private Long companyType_id;

    private Long mianIndustry_id;

    private Long auditingusertype_id;

    private String tel;

    private byte[] location;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Double getAssets() {
        return assets;
    }

    public void setAssets(Double assets) {
        this.assets = assets;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Double getCovered() {
        return covered;
    }

    public void setCovered(Double covered) {
        this.covered = covered;
    }

    public Integer getDevice_num() {
        return device_num;
    }

    public void setDevice_num(Integer device_num) {
        this.device_num = device_num;
    }

    public Integer getEmployee_num() {
        return employee_num;
    }

    public void setEmployee_num(Integer employee_num) {
        this.employee_num = employee_num;
    }

    public Double getFixed_assets() {
        return fixed_assets;
    }

    public void setFixed_assets(Double fixed_assets) {
        this.fixed_assets = fixed_assets;
    }

    public String getLocation_detail() {
        return location_detail;
    }

    public void setLocation_detail(String location_detail) {
        this.location_detail = location_detail == null ? null : location_detail.trim();
    }

    public Integer getObtainYear() {
        return obtainYear;
    }

    public void setObtainYear(Integer obtainYear) {
        this.obtainYear = obtainYear;
    }

    public String getOpenBankNo() {
        return openBankNo;
    }

    public void setOpenBankNo(String openBankNo) {
        this.openBankNo = openBankNo == null ? null : openBankNo.trim();
    }

    public String getOpen_bank_name() {
        return open_bank_name;
    }

    public void setOpen_bank_name(String open_bank_name) {
        this.open_bank_name = open_bank_name == null ? null : open_bank_name.trim();
    }

    public String getOpen_branch_name() {
        return open_branch_name;
    }

    public void setOpen_branch_name(String open_branch_name) {
        this.open_branch_name = open_branch_name == null ? null : open_branch_name.trim();
    }

    public String getOpen_branch_no() {
        return open_branch_no;
    }

    public void setOpen_branch_no(String open_branch_no) {
        this.open_branch_no = open_branch_no == null ? null : open_branch_no.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Boolean getRent() {
        return rent;
    }

    public void setRent(Boolean rent) {
        this.rent = rent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public Double getyTurnover() {
        return yTurnover;
    }

    public void setyTurnover(Double yTurnover) {
        this.yTurnover = yTurnover;
    }

    public Long getArea_id() {
        return area_id;
    }

    public void setArea_id(Long area_id) {
        this.area_id = area_id;
    }

    public Long getCardType_id() {
        return cardType_id;
    }

    public void setCardType_id(Long cardType_id) {
        this.cardType_id = cardType_id;
    }

    public Long getCompanyType_id() {
        return companyType_id;
    }

    public void setCompanyType_id(Long companyType_id) {
        this.companyType_id = companyType_id;
    }

    public Long getMianIndustry_id() {
        return mianIndustry_id;
    }

    public void setMianIndustry_id(Long mianIndustry_id) {
        this.mianIndustry_id = mianIndustry_id;
    }

    public Long getAuditingusertype_id() {
        return auditingusertype_id;
    }

    public void setAuditingusertype_id(Long auditingusertype_id) {
        this.auditingusertype_id = auditingusertype_id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public byte[] getLocation() {
        return location;
    }

    public void setLocation(byte[] location) {
        this.location = location;
    }
}