package com.sanbang.bean;

import java.util.Date;

/**
 * 退货附件
 * @author LENOVO
 *
 */
public class ezs_return_attach {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String fileName;//生成的文件名

    private String remark;//备注

    private Integer type;//附件类型    0是支付附件

    private String urlpath;//url地址

    private Long setorder_id;//当前退货记录id

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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrlpath() {
        return urlpath;
    }

    public void setUrlpath(String urlpath) {
        this.urlpath = urlpath == null ? null : urlpath.trim();
    }

    public Long getSetorder_id() {
        return setorder_id;
    }

    public void setSetorder_id(Long setorder_id) {
        this.setorder_id = setorder_id;
    }
}