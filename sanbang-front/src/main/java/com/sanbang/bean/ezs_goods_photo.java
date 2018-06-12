package com.sanbang.bean;

public class ezs_goods_photo {

	private Long goods_id;

    private Long photo_id;
    
    private ezs_accessory photo;
    

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(Long photo_id) {
        this.photo_id = photo_id;
    }

	public ezs_accessory getPhoto() {
		return photo;
	}

	public void setPhoto(ezs_accessory photo) {
		this.photo = photo;
	}

    
}