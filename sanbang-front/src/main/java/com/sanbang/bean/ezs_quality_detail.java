package com.sanbang.bean;

public class ezs_quality_detail {
    private Long quality_id;

    private Long detail_id;
    
    private ezs_qualityitem ezs_qualityitem;

    public Long getQuality_id() {
        return quality_id;
    }

    public void setQuality_id(Long quality_id) {
        this.quality_id = quality_id;
    }

    public Long getDetail_id() {
        return detail_id;
    }

    public void setDetail_id(Long detail_id) {
        this.detail_id = detail_id;
    }

	public ezs_qualityitem getEzs_qualityitem() {
		return ezs_qualityitem;
	}

	public void setEzs_qualityitem(ezs_qualityitem ezs_qualityitem) {
		this.ezs_qualityitem = ezs_qualityitem;
	}
    
    
}