package com.sanbang.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ezs_check_order_items implements Comparable<ezs_check_order_items>  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private Date lastModifyDate; 

    private Date deliveryDate;

    private String item_name;//产品名称

    private BigDecimal item_count;//数量

    private BigDecimal item_price;//单价

    private BigDecimal item_totalmoney;//总额

    private String flag;//是否参与计算标记，0：参与计算；1：不参与计算，默认参与计算

    private Long checkOrderMain_id;
    
    private String detail;//明细
    
   

    public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name == null ? null : item_name.trim();
    }

    public BigDecimal getItem_count() {
        return item_count;
    }

    public void setItem_count(BigDecimal item_count) {
        this.item_count = item_count;
    }

    public BigDecimal getItem_price() {
        return item_price;
    }

    public void setItem_price(BigDecimal item_price) {
        this.item_price = item_price;
    }

    public BigDecimal getItem_totalmoney() {
        return item_totalmoney;
    }

    public void setItem_totalmoney(BigDecimal item_totalmoney) {
        this.item_totalmoney = item_totalmoney;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public Long getCheckOrderMain_id() {
        return checkOrderMain_id;
    }

    public void setCheckOrderMain_id(Long checkOrderMain_id) {
        this.checkOrderMain_id = checkOrderMain_id;
    }

	@Override
	public int compareTo(ezs_check_order_items o) {
		String cata = "商品名称,吨袋扣款,吨袋扣重,其他费用";
		if(this.id==0&&o.id==0) {
			String  item_name0=this.getItem_name();
			String item_name1=o.getItem_name();
			if(cata.indexOf(item_name0)>cata.indexOf(item_name1)) {
				return 1;
			}else {
				return -1;
			}
		}else {
			if(this.id>o.id) {
				return 1;
			}else {
				return -1;
			}	
		}
		
	}

	
}