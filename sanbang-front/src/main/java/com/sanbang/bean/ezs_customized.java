package com.sanbang.bean;

import java.util.Date;

public class ezs_customized {
    private Integer id;

    private String colour;

    private String shape;

    private Double density;

    private Double xbforce;

    private Double jzforce;

    private Double melt_index;

    private Double ash_content;

    private Double water_content;

    private Double tensile;

    private Double elong_break;

    private Double bend_strength;

    private Double flexural_modulus;

    private Double combustion_grade;

    private String is_ep;

    private String purpose;

    private Double pre_num;

    private Double budget;

    private Date pre_time;

    private String sourcefrom;

    private Integer purchaser_id;

    private String remark;

    private Integer category_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour == null ? null : colour.trim();
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape == null ? null : shape.trim();
    }

    public Double getDensity() {
        return density;
    }

    public void setDensity(Double density) {
        this.density = density;
    }

    public Double getXbforce() {
        return xbforce;
    }

    public void setXbforce(Double xbforce) {
        this.xbforce = xbforce;
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

    public Double getAsh_content() {
        return ash_content;
    }

    public void setAsh_content(Double ash_content) {
        this.ash_content = ash_content;
    }

    public Double getWater_content() {
        return water_content;
    }

    public void setWater_content(Double water_content) {
        this.water_content = water_content;
    }

    public Double getTensile() {
        return tensile;
    }

    public void setTensile(Double tensile) {
        this.tensile = tensile;
    }

    public Double getElong_break() {
        return elong_break;
    }

    public void setElong_break(Double elong_break) {
        this.elong_break = elong_break;
    }

    public Double getBend_strength() {
        return bend_strength;
    }

    public void setBend_strength(Double bend_strength) {
        this.bend_strength = bend_strength;
    }

    public Double getFlexural_modulus() {
        return flexural_modulus;
    }

    public void setFlexural_modulus(Double flexural_modulus) {
        this.flexural_modulus = flexural_modulus;
    }

    public Double getCombustion_grade() {
        return combustion_grade;
    }

    public void setCombustion_grade(Double combustion_grade) {
        this.combustion_grade = combustion_grade;
    }

    public String getIs_ep() {
        return is_ep;
    }

    public void setIs_ep(String is_ep) {
        this.is_ep = is_ep == null ? null : is_ep.trim();
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public Double getPre_num() {
        return pre_num;
    }

    public void setPre_num(Double pre_num) {
        this.pre_num = pre_num;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Date getPre_time() {
        return pre_time;
    }

    public void setPre_time(Date pre_time) {
        this.pre_time = pre_time;
    }

    public String getSourcefrom() {
        return sourcefrom;
    }

    public void setSourcefrom(String sourcefrom) {
        this.sourcefrom = sourcefrom == null ? null : sourcefrom.trim();
    }

    public Integer getPurchaser_id() {
        return purchaser_id;
    }

    public void setPurchaser_id(Integer purchaser_id) {
        this.purchaser_id = purchaser_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
}