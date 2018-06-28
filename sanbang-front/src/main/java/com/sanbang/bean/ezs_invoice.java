package com.sanbang.bean;

import java.util.Date;

public class ezs_invoice {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String express_name;// 邮寄方式,ems,顺丰，韵达等

    private String express_no;// 快递号

    private Date express_time;// 开票时间

    private Integer invoice_status;// 开票状态 1.未配送 2.已配送 3.已完成

    private Long receipt_id;// 票据

    private Long user_id;

    private String order_no;
    
    private String picurl;
    
    private Date finish_time;// 完成时间

    private Integer flag;//票据类型（1.买家 2.卖家）
    
    private ezs_accessory  accessory;//图片
    
    private String apply_no;

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

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Date getFinish_time() {
		return finish_time;
	}

	public void setFinish_time(Date finish_time) {
		this.finish_time = finish_time;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public ezs_accessory getAccessory() {
		return accessory;
	}

	public void setAccessory(ezs_accessory accessory) {
		this.accessory = accessory;
	}

	public String getApply_no() {
		return apply_no;
	}

	public void setApply_no(String apply_no) {
		this.apply_no = apply_no;
	}
}