package com.sanbang.bean;

public class ezs_industry_dict {
    private Long industry_id;

    private Long dict_id;

    public Long getIndustry_id() {
        return industry_id;
    }

    public void setIndustry_id(Long industry_id) {
        this.industry_id = industry_id;
    }

    public Long getDict_id() {
        return dict_id;
    }

    public void setDict_id(Long dict_id) {
        this.dict_id = dict_id;
    }

	public ezs_industry_dict(Long industry_id, Long dict_id) {
		super();
		this.industry_id = industry_id;
		this.dict_id = dict_id;
	}
    
}