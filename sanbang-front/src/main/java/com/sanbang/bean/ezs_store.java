package com.sanbang.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 商城表
 */

public class ezs_store implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private Date addTime;// 添加时间

	private Boolean deleteStatus;// 是否启用（0启用1不启用

	private String address;// 经营地址

	private Double assets;// 总资产

	private String companyName;// 企业名称

	private Double covered;// 占地面积

	private Integer device_num;// 设备数量

	private Integer employee_num;// 员工数量

	private Double fixed_assets;// 固定资产

	private String location_detail;// 开户银行所在地

	private Integer obtainYear;// 实际控制人从业年限

	private String openBankNo;// 开户行账号

	private String open_bank_name;// 开户行名称

	private String open_branch_name;// 开户银行支行名称

	private String open_branch_no;// 开户支行账号

	private Date registerDate;// 注册时间

	private Boolean rent;// 租用

	private Integer status;// 0:初始数据无业务 审核状态 1:需要审核 2.审核通过,3审核未通过

	private String userType;// 1.SELLER卖家，2.BUYER:买家

	private Double yTurnover;// 年营业额

	private Long area_id;// 经营地址区县

	private Long cardType_id;// 证件类型

	private Long companyType_id;// 公司类型

	private Long mianIndustry_id;// 主营行业

	private Long auditingusertype_id;// 1, 线索用户 2, 注册用户, 3, 认证用户 ,4, 激活用户

	private String tel;// 联系电话

	private Integer accountType;// 1.企业账号，2.个体户

	private Integer admin_status;// 管理状态(1.分派，2.认领)

	private Double capitalPrice;//

	private String account;// 注册号

	private String unifyCode;// 社会信用代码

	private String person;// 法人

	private Long regArea_id;// 注册区县ID

	private String regAddress;// 注册地址

	private String idCardNum;// 经营者省份证号

	private Integer creditScore;

	private String number;

	private String snumber;

	private Long customerAudit_id;

	private String creditLevel;

	private Long location_id;

	private int name_status;// 是否白名单

	private Boolean business;// 是否有效 true 长期有效 false 不是长期有效

	private Date businessCardTime; // 有效期时间

	private Integer adjust_status;

	private String adjust_type;

	private Long currentAdjustApply_id;

	private Integer operateYear;

	private String child_company_status;//是否子公司；0：是；1：否

	private Date auditDate;

	private String temporary;

	public Long getId() {
		return id;
	}

	public Integer getOperateYear() {
		return operateYear;
	}

	public void setOperateYear(Integer operateYear) {
		this.operateYear = operateYear;
	}

	public String getChild_company_status() {
		return child_company_status;
	}

	public void setChild_company_status(String child_company_status) {
		this.child_company_status = child_company_status;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getTemporary() {
		return temporary;
	}

	public void setTemporary(String temporary) {
		this.temporary = temporary;
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

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Integer getAdmin_status() {
		return admin_status;
	}

	public void setAdmin_status(Integer admin_status) {
		this.admin_status = admin_status;
	}

	public Double getCapitalPrice() {
		return capitalPrice;
	}

	public void setCapitalPrice(Double capitalPrice) {
		this.capitalPrice = capitalPrice;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUnifyCode() {
		return unifyCode;
	}

	public void setUnifyCode(String unifyCode) {
		this.unifyCode = unifyCode;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public Long getRegArea_id() {
		return regArea_id;
	}

	public void setRegArea_id(Long regArea_id) {
		this.regArea_id = regArea_id;
	}

	public String getRegAddress() {
		return regAddress;
	}

	public void setRegAddress(String regAddress) {
		this.regAddress = regAddress;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public Integer getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(Integer creditScore) {
		this.creditScore = creditScore;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getCustomerAudit_id() {
		return customerAudit_id;
	}

	public void setCustomerAudit_id(Long customerAudit_id) {
		this.customerAudit_id = customerAudit_id;
	}

	public String getCreditLevel() {
		return creditLevel;
	}

	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public String getSnumber() {
		return snumber;
	}

	public void setSnumber(String snumber) {
		this.snumber = snumber;
	}

	public int getName_status() {
		return name_status;
	}

	public void setName_status(int name_status) {
		this.name_status = name_status;
	}

	public Boolean getBusiness() {
		return business;
	}

	public void setBusiness(Boolean business) {
		this.business = business;
	}

	public Date getBusinessCardTime() {
		return businessCardTime;
	}

	public void setBusinessCardTime(Date businessCardTime) {
		this.businessCardTime = businessCardTime;
	}

	public Integer getAdjust_status() {
		return adjust_status;
	}

	public void setAdjust_status(Integer adjust_status) {
		this.adjust_status = adjust_status;
	}

	public String getAdjust_type() {
		return adjust_type;
	}

	public void setAdjust_type(String adjust_type) {
		this.adjust_type = adjust_type;
	}

	public Long getCurrentAdjustApply_id() {
		return currentAdjustApply_id;
	}

	public void setCurrentAdjustApply_id(Long currentAdjustApply_id) {
		this.currentAdjustApply_id = currentAdjustApply_id;
	}

	@Override
	public String toString() {
		return "ezs_store [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus + ", address="
				+ address + ", assets=" + assets + ", companyName=" + companyName + ", covered=" + covered
				+ ", device_num=" + device_num + ", employee_num=" + employee_num + ", fixed_assets=" + fixed_assets
				+ ", location_detail=" + location_detail + ", obtainYear=" + obtainYear + ", openBankNo=" + openBankNo
				+ ", open_bank_name=" + open_bank_name + ", open_branch_name=" + open_branch_name + ", open_branch_no="
				+ open_branch_no + ", registerDate=" + registerDate + ", rent=" + rent + ", status=" + status
				+ ", userType=" + userType + ", yTurnover=" + yTurnover + ", area_id=" + area_id + ", cardType_id="
				+ cardType_id + ", companyType_id=" + companyType_id + ", mianIndustry_id=" + mianIndustry_id
				+ ", auditingusertype_id=" + auditingusertype_id + ", tel=" + tel + ", accountType=" + accountType
				+ ", admin_status=" + admin_status + ", capitalPrice=" + capitalPrice + ", account=" + account
				+ ", unifyCode=" + unifyCode + ", person=" + person + ", regArea_id=" + regArea_id + ", regAddress="
				+ regAddress + ", idCardNum=" + idCardNum + ", creditScore=" + creditScore + ", number=" + number
				+ ", snumber=" + snumber + ", customerAudit_id=" + customerAudit_id + ", creditLevel=" + creditLevel
				+ ", location_id=" + location_id + ", name_status=" + name_status + ", business=" + business
				+ ", businessCardTime=" + businessCardTime + ", adjust_status=" + adjust_status + ", adjust_type="
				+ adjust_type + ", currentAdjustApply_id=" + currentAdjustApply_id + ", operateYear=" + operateYear
				+ ", child_company_status=" + child_company_status + ", auditDate=" + auditDate + ", temporary="
				+ temporary + "]";
	}

}