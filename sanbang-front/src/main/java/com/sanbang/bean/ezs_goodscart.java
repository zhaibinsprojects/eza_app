package com.sanbang.bean;

import java.math.BigDecimal;
import java.util.Date;

public class ezs_goodscart {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String cart_type;

    private Double count;

    private BigDecimal price;

    private Long goods_id;

    private Long of_id;

    private Long sc_id;
    
    private String atime;
    private Double acount;//销售数量
    private BigDecimal aprice;//销售额
    private int updatecount;//更新次数
    

    public String getAtime() {
		return atime;
	}

	public void setAtime(String atime) {
		this.atime = atime;
	}

	public Double getAcount() {
		return acount;
	}

	public void setAcount(Double acount) {
		this.acount = acount;
	}

	public BigDecimal getAprice() {
		return aprice;
	}

	public void setAprice(BigDecimal aprice) {
		this.aprice = aprice;
	}

	public int getUpdatecount() {
		return updatecount;
	}

	public void setUpdatecount(int updatecount) {
		this.updatecount = updatecount;
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

    public String getCart_type() {
        return cart_type;
    }

    public void setCart_type(String cart_type) {
        this.cart_type = cart_type == null ? null : cart_type.trim();
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getOf_id() {
        return of_id;
    }

    public void setOf_id(Long of_id) {
        this.of_id = of_id;
    }

    public Long getSc_id() {
        return sc_id;
    }

    public void setSc_id(Long sc_id) {
        this.sc_id = sc_id;
    }
}