package com.sanbang.bean;

public class ezs_checkm_photo {
    private Long ckmid;

    private Long acid;
    
    private ezs_accessory accessory;

    public Long getCkmid() {
        return ckmid;
    }

    public void setCkmid(Long ckmid) {
        this.ckmid = ckmid;
    }

    public Long getAcid() {
        return acid;
    }

    public void setAcid(Long acid) {
        this.acid = acid;
    }

	public ezs_accessory getAccessory() {
		return accessory;
	}

	public void setAccessory(ezs_accessory accessory) {
		this.accessory = accessory;
	}

	public ezs_checkm_photo(Long ckmid, Long acid, ezs_accessory accessory) {
		super();
		this.ckmid = ckmid;
		this.acid = acid;
		this.accessory = accessory;
	}
    public ezs_checkm_photo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ezs_checkm_photo [ckmid=" + ckmid + ", acid=" + acid + ", accessory=" + accessory + "]";
	}
    
    
    
}