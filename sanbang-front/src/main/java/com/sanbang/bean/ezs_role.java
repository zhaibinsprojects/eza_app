package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;

public class ezs_role implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8388287992714663928L;

	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String storeID;

    private Boolean display;

    private String info;

    private String name;

    private Integer sequence;

    private String type;

    private String roleCode;

    private Long roleGroup_id;

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

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID == null ? null : storeID.trim();
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public Long getRoleGroup_id() {
        return roleGroup_id;
    }

    public void setRoleGroup_id(Long roleGroup_id) {
        this.roleGroup_id = roleGroup_id;
    }

	@Override
	public String toString() {
		return "ezs_role [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus + ", storeID=" + storeID
				+ ", display=" + display + ", info=" + info + ", name=" + name + ", sequence=" + sequence + ", type="
				+ type + ", roleCode=" + roleCode + ", roleGroup_id=" + roleGroup_id + "]";
	}
    
    
}