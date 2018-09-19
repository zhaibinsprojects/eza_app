package com.sanbang.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ezs_check_order_main implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Date lastModifyDate;// 商品更新日期，即最近一次修改的日期

    private String order_no;// 订单编号

    private BigDecimal order_money;//订单金额

    private BigDecimal imblance_money;//应付、应退金额,值>0:表示实际付款金额较少，需补交；值<0：表示实际付款金额较多，需退款。

    private String memo;//备注
    
    public List<ezs_check_order_items> getItems() {
		return items;
	}

	public void setItems(List<ezs_check_order_items> items) {
		this.items = items;
	}

	List<ezs_check_order_items> items;//对账单历史记录详情

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

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    public BigDecimal getOrder_money() {
        return order_money;
    }

    public void setOrder_money(BigDecimal order_money) {
        this.order_money = order_money;
    }

    public BigDecimal getImblance_money() {
        return imblance_money;
    }

    public void setImblance_money(BigDecimal imblance_money) {
        this.imblance_money = imblance_money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	@Override
	public String toString() {
		return "ezs_check_order_main [id=" + id + ", addTime=" + addTime + ", deleteStatus=" + deleteStatus
				+ ", lastModifyDate=" + lastModifyDate + ", order_no=" + order_no + ", order_money=" + order_money
				+ ", imblance_money=" + imblance_money + ", memo=" + memo + ", items=" + items + "]";
	}
    
    
}