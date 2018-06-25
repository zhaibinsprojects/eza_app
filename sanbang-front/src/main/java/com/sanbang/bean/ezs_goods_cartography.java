package com.sanbang.bean;

public class ezs_goods_cartography {
    private Long goods_id;

    private Long cartography_id;
    
    private ezs_accessory photo;

    public Long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(Long goods_id) {
        this.goods_id = goods_id;
    }

    public Long getCartography_id() {
        return cartography_id;
    }

    public void setCartography_id(Long cartography_id) {
        this.cartography_id = cartography_id;
    }

	public ezs_accessory getPhoto() {
		return photo;
	}

	public void setPhoto(ezs_accessory photo) {
		this.photo = photo;
	}
    
    
}