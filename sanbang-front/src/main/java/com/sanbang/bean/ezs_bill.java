package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;

public class ezs_bill implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5031913451410226434L;

	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String address;

    private String bank;

    private String companyName;

    private String dutyNo;

    private String number;

    private String phone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank == null ? null : bank.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getDutyNo() {
        return dutyNo;
    }

    public void setDutyNo(String dutyNo) {
        this.dutyNo = dutyNo == null ? null : dutyNo.trim();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

	@Override
	public String toString() {
		return "ezs_bill [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus + ", address=" + address
				+ ", bank=" + bank + ", companyName=" + companyName + ", dutyNo=" + dutyNo + ", number=" + number
				+ ", phone=" + phone + ", user_id=" + user_id + "]";
	}
    
}