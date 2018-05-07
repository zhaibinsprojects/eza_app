package com.sanbang.bean;

import java.util.Date;

public class ezs_invoice {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String express_name;

    private String express_no;

    private Date express_time;

    private Integer invoice_status;

    private Long receipt_id;

    private Long user_id;

    private String order_no;

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

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name == null ? null : express_name.trim();
    }

    public String getExpress_no() {
        return express_no;
    }

    public void setExpress_no(String express_no) {
        this.express_no = express_no == null ? null : express_no.trim();
    }

    public Date getExpress_time() {
        return express_time;
    }

    public void setExpress_time(Date express_time) {
        this.express_time = express_time;
    }

    public Integer getInvoice_status() {
        return invoice_status;
    }

    public void setInvoice_status(Integer invoice_status) {
        this.invoice_status = invoice_status;
    }

    public Long getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(Long receipt_id) {
        this.receipt_id = receipt_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }
}