package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;

public class ezs_userinfo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5881163343819974853L;

	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String email;

    private Boolean enable;

    private Date entryTime;

    private String phone;

    private Integer status;

    private Long depart_id;

    private Long position_id;

    private Long sex_id;

    private String QQ;

    private Integer updateStatus;
    
    private String tel;
    

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDepart_id() {
        return depart_id;
    }

    public void setDepart_id(Long depart_id) {
        this.depart_id = depart_id;
    }

    public Long getPosition_id() {
        return position_id;
    }

    public void setPosition_id(Long position_id) {
        this.position_id = position_id;
    }

    public Long getSex_id() {
        return sex_id;
    }

    public void setSex_id(Long sex_id) {
        this.sex_id = sex_id;
    }

    public String getQQ() {
        return QQ;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ == null ? null : QQ.trim();
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Override
	public String toString() {
		return "ezs_userinfo [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus + ", email=" + email
				+ ", enable=" + enable + ", entryTime=" + entryTime + ", phone=" + phone + ", status=" + status
				+ ", depart_id=" + depart_id + ", position_id=" + position_id + ", sex_id=" + sex_id + ", QQ=" + QQ
				+ ", updateStatus=" + updateStatus + ", tel=" + tel + "]";
	}
    
    
    
}