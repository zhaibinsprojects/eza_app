package com.sanbang.bean;

import java.util.Date;
import java.util.List;

public class ezs_customized {
	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String address;

    private String ashContent;

    private String attr1;

    private String attr2;

    private String attr3;

    private String bendStrength;

    private Double budget;

    private String combustionGrade;

    private String density;

    private String elongBreak;

    private String flexuralModulus;

    private String isEp;

    private String jzforce;

    private String meltIndex;

    private Date modifyDate;

    private String modifyReason;

    private Double preNum;

    private Date preTime;

    private String proName;

    private String purpose;

    private String remark;

    private String sourceType;

    private String sourcefrom;

    private String status;

    private String tensile;

    private String waterContent;

    private String xbforce;

    private Long addres_id;

    private Long category_id;

    private Long colour_id;

    private Long goods_id;

    private Long modifyUser_id;

    private Long purchaser_id;

    private Long shape_id;

    private Long rootParentId;

    private String ash_content;

    private String bend_strength;

    private String combustion_grade;

    private String elong_break;

    private String flexural_modulus;

    private String is_ep;

    private Double melt_index;

    private Double pre_num;

    private Date pre_time;

    private String source_type;

    private String water_content;
    //新增字段
    private String colour;
    //新增字段
    private String shape;
    
    private String cname;
    
    private String catname;
    
    private List<ezs_customized_record> recordlist;
    
    private List<ezs_customized_res> reslist;

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
		this.address = address;
	}

	public String getAshContent() {
		return ashContent;
	}

	public void setAshContent(String ashContent) {
		this.ashContent = ashContent;
	}

	public String getAttr1() {
		return attr1;
	}

	public void setAttr1(String attr1) {
		this.attr1 = attr1;
	}

	public String getAttr2() {
		return attr2;
	}

	public void setAttr2(String attr2) {
		this.attr2 = attr2;
	}

	public String getAttr3() {
		return attr3;
	}

	public void setAttr3(String attr3) {
		this.attr3 = attr3;
	}

	public String getBendStrength() {
		return bendStrength;
	}

	public void setBendStrength(String bendStrength) {
		this.bendStrength = bendStrength;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getCombustionGrade() {
		return combustionGrade;
	}

	public void setCombustionGrade(String combustionGrade) {
		this.combustionGrade = combustionGrade;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getElongBreak() {
		return elongBreak;
	}

	public void setElongBreak(String elongBreak) {
		this.elongBreak = elongBreak;
	}

	public String getFlexuralModulus() {
		return flexuralModulus;
	}

	public void setFlexuralModulus(String flexuralModulus) {
		this.flexuralModulus = flexuralModulus;
	}

	public String getIsEp() {
		return isEp;
	}

	public void setIsEp(String isEp) {
		this.isEp = isEp;
	}

	public String getJzforce() {
		return jzforce;
	}

	public void setJzforce(String jzforce) {
		this.jzforce = jzforce;
	}

	public String getMeltIndex() {
		return meltIndex;
	}

	public void setMeltIndex(String meltIndex) {
		this.meltIndex = meltIndex;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyReason() {
		return modifyReason;
	}

	public void setModifyReason(String modifyReason) {
		this.modifyReason = modifyReason;
	}

	public Double getPreNum() {
		return preNum;
	}

	public void setPreNum(Double preNum) {
		this.preNum = preNum;
	}

	public Date getPreTime() {
		return preTime;
	}

	public void setPreTime(Date preTime) {
		this.preTime = preTime;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getSourcefrom() {
		return sourcefrom;
	}

	public void setSourcefrom(String sourcefrom) {
		this.sourcefrom = sourcefrom;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTensile() {
		return tensile;
	}

	public void setTensile(String tensile) {
		this.tensile = tensile;
	}

	public String getWaterContent() {
		return waterContent;
	}

	public void setWaterContent(String waterContent) {
		this.waterContent = waterContent;
	}

	public String getXbforce() {
		return xbforce;
	}

	public void setXbforce(String xbforce) {
		this.xbforce = xbforce;
	}

	public Long getAddres_id() {
		return addres_id;
	}

	public void setAddres_id(Long addres_id) {
		this.addres_id = addres_id;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public Long getColour_id() {
		return colour_id;
	}

	public void setColour_id(Long colour_id) {
		this.colour_id = colour_id;
	}

	public Long getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getModifyUser_id() {
		return modifyUser_id;
	}

	public void setModifyUser_id(Long modifyUser_id) {
		this.modifyUser_id = modifyUser_id;
	}

	public Long getPurchaser_id() {
		return purchaser_id;
	}

	public void setPurchaser_id(Long purchaser_id) {
		this.purchaser_id = purchaser_id;
	}

	public Long getShape_id() {
		return shape_id;
	}

	public void setShape_id(Long shape_id) {
		this.shape_id = shape_id;
	}

	public Long getRootParentId() {
		return rootParentId;
	}

	public void setRootParentId(Long rootParentId) {
		this.rootParentId = rootParentId;
	}

	public String getAsh_content() {
		return ash_content;
	}

	public void setAsh_content(String ash_content) {
		this.ash_content = ash_content;
	}

	public String getBend_strength() {
		return bend_strength;
	}

	public void setBend_strength(String bend_strength) {
		this.bend_strength = bend_strength;
	}

	public String getCombustion_grade() {
		return combustion_grade;
	}

	public void setCombustion_grade(String combustion_grade) {
		this.combustion_grade = combustion_grade;
	}

	public String getElong_break() {
		return elong_break;
	}

	public void setElong_break(String elong_break) {
		this.elong_break = elong_break;
	}

	public String getFlexural_modulus() {
		return flexural_modulus;
	}

	public void setFlexural_modulus(String flexural_modulus) {
		this.flexural_modulus = flexural_modulus;
	}

	public String getIs_ep() {
		return is_ep;
	}

	public void setIs_ep(String is_ep) {
		this.is_ep = is_ep;
	}

	public Double getMelt_index() {
		return melt_index;
	}

	public void setMelt_index(Double melt_index) {
		this.melt_index = melt_index;
	}

	public Double getPre_num() {
		return pre_num;
	}

	public void setPre_num(Double pre_num) {
		this.pre_num = pre_num;
	}

	public Date getPre_time() {
		return pre_time;
	}

	public void setPre_time(Date pre_time) {
		this.pre_time = pre_time;
	}

	public String getSource_type() {
		return source_type;
	}

	public void setSource_type(String source_type) {
		this.source_type = source_type;
	}

	public String getWater_content() {
		return water_content;
	}

	public void setWater_content(String water_content) {
		this.water_content = water_content;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCatname() {
		return catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public List<ezs_customized_record> getRecordlist() {
		return recordlist;
	}

	public void setRecordlist(List<ezs_customized_record> recordlist) {
		this.recordlist = recordlist;
	}

	public List<ezs_customized_res> getReslist() {
		return reslist;
	}

	public void setReslist(List<ezs_customized_res> reslist) {
		this.reslist = reslist;
	}
    
}