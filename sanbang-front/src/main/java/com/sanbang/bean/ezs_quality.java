package com.sanbang.bean;

import java.util.Date;
import java.util.List;

public class ezs_quality {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Date arrivalDate;

    private String bUser;

    private String bnumber;

    private String cchkconclusion;

    private String cdefine9;

    private String cprojectname;

    private Date dverifytime;

    private String fdisbreakquantity;

    private String fdisubreakquantity;

    private String fdtquantity;

    private String fdtrate;

    private String fquantity;

    private String ibatchchkresult;

    private String qUser;

    private String rnumber;
    
    private List<ezs_quality_detail>  items;

   

	public List<ezs_quality_detail> getItems() {
		return items;
	}

	public void setItems(List<ezs_quality_detail> items) {
		this.items = items;
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

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getbUser() {
        return bUser;
    }

    public void setbUser(String bUser) {
        this.bUser = bUser == null ? null : bUser.trim();
    }

    public String getBnumber() {
        return bnumber;
    }

    public void setBnumber(String bnumber) {
        this.bnumber = bnumber == null ? null : bnumber.trim();
    }

    public String getCchkconclusion() {
        return cchkconclusion;
    }

    public void setCchkconclusion(String cchkconclusion) {
        this.cchkconclusion = cchkconclusion == null ? null : cchkconclusion.trim();
    }

    public String getCdefine9() {
        return cdefine9;
    }

    public void setCdefine9(String cdefine9) {
        this.cdefine9 = cdefine9 == null ? null : cdefine9.trim();
    }

    public String getCprojectname() {
        return cprojectname;
    }

    public void setCprojectname(String cprojectname) {
        this.cprojectname = cprojectname == null ? null : cprojectname.trim();
    }

    public Date getDverifytime() {
        return dverifytime;
    }

    public void setDverifytime(Date dverifytime) {
        this.dverifytime = dverifytime;
    }

    public String getFdisbreakquantity() {
        return fdisbreakquantity;
    }

    public void setFdisbreakquantity(String fdisbreakquantity) {
        this.fdisbreakquantity = fdisbreakquantity == null ? null : fdisbreakquantity.trim();
    }

    public String getFdisubreakquantity() {
        return fdisubreakquantity;
    }

    public void setFdisubreakquantity(String fdisubreakquantity) {
        this.fdisubreakquantity = fdisubreakquantity == null ? null : fdisubreakquantity.trim();
    }

    public String getFdtquantity() {
        return fdtquantity;
    }

    public void setFdtquantity(String fdtquantity) {
        this.fdtquantity = fdtquantity == null ? null : fdtquantity.trim();
    }

    public String getFdtrate() {
        return fdtrate;
    }

    public void setFdtrate(String fdtrate) {
        this.fdtrate = fdtrate == null ? null : fdtrate.trim();
    }

    public String getFquantity() {
        return fquantity;
    }

    public void setFquantity(String fquantity) {
        this.fquantity = fquantity == null ? null : fquantity.trim();
    }

    public String getIbatchchkresult() {
        return ibatchchkresult;
    }

    public void setIbatchchkresult(String ibatchchkresult) {
        this.ibatchchkresult = ibatchchkresult == null ? null : ibatchchkresult.trim();
    }

    public String getqUser() {
        return qUser;
    }

    public void setqUser(String qUser) {
        this.qUser = qUser == null ? null : qUser.trim();
    }

    public String getRnumber() {
        return rnumber;
    }

    public void setRnumber(String rnumber) {
        this.rnumber = rnumber == null ? null : rnumber.trim();
    }
}