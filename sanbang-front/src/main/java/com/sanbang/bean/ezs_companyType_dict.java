package com.sanbang.bean;

public class ezs_companyType_dict {
    private Long cType_id;

    private Long dict_id;

    public Long getcType_id() {
        return cType_id;
    }

    public void setcType_id(Long cType_id) {
        this.cType_id = cType_id;
    }

    public Long getDict_id() {
        return dict_id;
    }

    public void setDict_id(Long dict_id) {
        this.dict_id = dict_id;
    }

    public ezs_companyType_dict() {
		// TODO Auto-generated constructor stub
	}
	public ezs_companyType_dict(Long cType_id, Long dict_id) {
		super();
		this.cType_id = cType_id;
		this.dict_id = dict_id;
	}
    
    
}