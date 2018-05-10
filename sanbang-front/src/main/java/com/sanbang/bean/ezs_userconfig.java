package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;

public class ezs_userconfig implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5578758002842032649L;

	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

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

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

	@Override
	public String toString() {
		return "ezs_userconfig [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus + ", user_id="
				+ user_id + "]";
	}
    
}