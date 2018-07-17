package com.sanbang.bean;

import java.util.Date;
import java.util.List;

public class ezs_customized {

    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String address;

    private Double ash_content;

    private Double bend_strength;

    private Double budget;

    private String colour;

    private Double combustion_grade;

    private Double density;

    private Double elong_break;

    private Double flexural_modulus;

    private String is_ep;

    private Double jzforce;

    private Double melt_index;

    private Double pre_num;

    private Date pre_time;

    private String purpose;

    private String remark;

    private String shape;

    private String source_type;

    private String sourcefrom;

    private String status;

    private Double tensile;

    private Double water_content;

    private Double xbforce;

    private Long category_id;

    private Long goods_id;

    private Long purchaser_id;

    private String proName;

    private Long colour_id;

    private Long shape_id;
    
    private String cname;
    
    private String catname;//品类
    
    private List<ezs_customized_record> recordlist;
    
    private List<ezs_customized_res>  reslist;

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

	public Double getAsh_content() {
		return ash_content;
	}

	public void setAsh_content(Double ash_content) {
		this.ash_content = ash_content;
	}

	public Double getBend_strength() {
		return bend_strength;
	}

	public void setBend_strength(Double bend_strength) {
		this.bend_strength = bend_strength;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Double getCombustion_grade() {
		return combustion_grade;
	}

	public void setCombustion_grade(Double combustion_grade) {
		this.combustion_grade = combustion_grade;
	}

	public Double getDensity() {
		return density;
	}

	public void setDensity(Double density) {
		this.density = density;
	}

	public Double getElong_break() {
		return elong_break;
	}

	public void setElong_break(Double elong_break) {
		this.elong_break = elong_break;
	}

	public Double getFlexural_modulus() {
		return flexural_modulus;
	}

	public void setFlexural_modulus(Double flexural_modulus) {
		this.flexural_modulus = flexural_modulus;
	}

	public String getIs_ep() {
		return is_ep;
	}

	public void setIs_ep(String is_ep) {
		this.is_ep = is_ep;
	}

	public Double getJzforce() {
		return jzforce;
	}

	public void setJzforce(Double jzforce) {
		this.jzforce = jzforce;
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

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public String getSource_type() {
		return source_type;
	}

	public void setSource_type(String source_type) {
		this.source_type = source_type;
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

	public Double getTensile() {
		return tensile;
	}

	public void setTensile(Double tensile) {
		this.tensile = tensile;
	}

	public Double getWater_content() {
		return water_content;
	}

	public void setWater_content(Double water_content) {
		this.water_content = water_content;
	}

	public Double getXbforce() {
		return xbforce;
	}

	public void setXbforce(Double xbforce) {
		this.xbforce = xbforce;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public Long getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(Long goods_id) {
		this.goods_id = goods_id;
	}

	public Long getPurchaser_id() {
		return purchaser_id;
	}

	public void setPurchaser_id(Long purchaser_id) {
		this.purchaser_id = purchaser_id;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public Long getColour_id() {
		return colour_id;
	}

	public void setColour_id(Long colour_id) {
		this.colour_id = colour_id;
	}

	public Long getShape_id() {
		return shape_id;
	}

	public void setShape_id(Long shape_id) {
		this.shape_id = shape_id;
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

	public ezs_customized(Long id, Date addTime, Boolean deleteStatus, String address, Double ash_content,
			Double bend_strength, Double budget, String colour, Double combustion_grade, Double density,
			Double elong_break, Double flexural_modulus, String is_ep, Double jzforce, Double melt_index,
			Double pre_num, Date pre_time, String purpose, String remark, String shape, String source_type,
			String sourcefrom, String status, Double tensile, Double water_content, Double xbforce, Long category_id,
			Long goods_id, Long purchaser_id, String proName, Long colour_id, Long shape_id, String cname,
			String catname, List<ezs_customized_record> recordlist, List<ezs_customized_res> reslist) {
		super();
		this.id = id;
		this.addTime = addTime;
		this.deleteStatus = deleteStatus;
		this.address = address;
		this.ash_content = ash_content;
		this.bend_strength = bend_strength;
		this.budget = budget;
		this.colour = colour;
		this.combustion_grade = combustion_grade;
		this.density = density;
		this.elong_break = elong_break;
		this.flexural_modulus = flexural_modulus;
		this.is_ep = is_ep;
		this.jzforce = jzforce;
		this.melt_index = melt_index;
		this.pre_num = pre_num;
		this.pre_time = pre_time;
		this.purpose = purpose;
		this.remark = remark;
		this.shape = shape;
		this.source_type = source_type;
		this.sourcefrom = sourcefrom;
		this.status = status;
		this.tensile = tensile;
		this.water_content = water_content;
		this.xbforce = xbforce;
		this.category_id = category_id;
		this.goods_id = goods_id;
		this.purchaser_id = purchaser_id;
		this.proName = proName;
		this.colour_id = colour_id;
		this.shape_id = shape_id;
		this.cname = cname;
		this.catname = catname;
		this.recordlist = recordlist;
		this.reslist = reslist;
	}
    public ezs_customized() {
		// TODO Auto-generated constructor stub
	}
}