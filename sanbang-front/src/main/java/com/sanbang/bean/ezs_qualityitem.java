package com.sanbang.bean;

import java.util.Date;

public class ezs_qualityitem {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String measure;

    private String name;

    private String product_format;

    private String q_result;

    private String term;

    private String unit;

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

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure == null ? null : measure.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getProduct_format() {
        return product_format;
    }

    public void setProduct_format(String product_format) {
        this.product_format = product_format == null ? null : product_format.trim();
    }

    public String getQ_result() {
        return q_result;
    }

    public void setQ_result(String q_result) {
        this.q_result = q_result == null ? null : q_result.trim();
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }
}