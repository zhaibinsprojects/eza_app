package com.sanbang.vo;

import java.util.Date;

import com.sanbang.bean.ezs_price_trend;

public class PriceTrendIfo extends ezs_price_trend {
	
	private String goodClassName;
	private String goodColorName;
	private String goodFormName;
	private Double goodPrice;
	private String goodArea;
	//当日均价
	private Double currentAVGPrice;
	//前一日均价
	private Double preAVGPrice;
	//成交日期
	private String dealDate;
    //当日价格
    private Double currentPrice;
    //前日价格
    private Double prePrice;
	//涨幅
	private Double increaseValue;
	
	private String sandByOne;
	//品类名称
	private String sandBytwo;
	private String sandBytree;
	
    private Long id;

    private Date addTime;

    private Double price;

    private int protection;

    private Integer type;

    private Long goodClass_id;

    private Long region_id;

    private Date data_time;

    private Integer price_type;
    //是否展示 0不展示，1展示
    private Integer isshow;

    public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Double getPrePrice() {
		return prePrice;
	}

	public void setPrePrice(Double prePrice) {
		this.prePrice = prePrice;
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

    public Integer getProtection() {
		return protection;
	}

	public void setProtection(int protection) {
		this.protection = protection;
	}

	public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    

   


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGoodClass_id() {
        return goodClass_id;
    }

    public void setGoodClass_id(Long goodClass_id) {
        this.goodClass_id = goodClass_id;
    }

    public Long getRegion_id() {
        return region_id;
    }

    public void setRegion_id(Long region_id) {
        this.region_id = region_id;
    }


    public Date getData_time() {
        return data_time;
    }

    public void setData_time(Date data_time) {
        this.data_time = data_time;
    }


	public Double getIncreaseValue() {
		return increaseValue;
	}

	public void setIncreaseValue(Double increaseValue) {
		this.increaseValue = increaseValue;
	}
    

    public Integer getPrice_type() {
        return price_type;
    }

    public void setPrice_type(Integer price_type) {
        this.price_type = price_type;
    }

	public String getGoodClassName() {
		return goodClassName;
	}
	public void setGoodClassName(String goodClassName) {
		this.goodClassName = goodClassName;
	}
	public String getGoodColorName() {
		return goodColorName;
	}
	public void setGoodColorName(String goodColorName) {
		this.goodColorName = goodColorName;
	}
	public String getGoodFormName() {
		return goodFormName;
	}
	public void setGoodFormName(String goodFormName) {
		this.goodFormName = goodFormName;
	}
	public Double getGoodPrice() {
		return goodPrice;
	}
	public void setGoodPrice(Double goodPrice) {
		this.goodPrice = goodPrice;
	}
	public String getGoodArea() {
		return goodArea;
	}
	public void setGoodArea(String goodArea) {
		this.goodArea = goodArea;
	}
	public String getSandByOne() {
		return sandByOne;
	}
	public void setSandByOne(String sandByOne) {
		this.sandByOne = sandByOne;
	}
	public String getSandBytwo() {
		return sandBytwo;
	}
	public void setSandBytwo(String sandBytwo) {
		this.sandBytwo = sandBytwo;
	}
	public String getSandBytree() {
		return sandBytree;
	}
	public void setSandBytree(String sandBytree) {
		this.sandBytree = sandBytree;
	}
	
	
	public PriceTrendIfo(String goodClassName, String goodColorName, String goodFormName, Double goodPrice,
			String goodArea) {
		super();
		this.goodClassName = goodClassName;
		this.goodColorName = goodColorName;
		this.goodFormName = goodFormName;
		this.goodPrice = goodPrice;
		this.goodArea = goodArea;
	}
	public PriceTrendIfo() {
		super();
	}
	public Double getCurrentAVGPrice() {
		return currentAVGPrice;
	}
	public void setCurrentAVGPrice(Double currentAVGPrice) {
		this.currentAVGPrice = currentAVGPrice;
	}
	public Double getPreAVGPrice() {
		return preAVGPrice;
	}
	public void setPreAVGPrice(Double preAVGPrice) {
		this.preAVGPrice = preAVGPrice;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
}
