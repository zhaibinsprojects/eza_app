package com.sanbang.vo;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.bean.ezs_goods;

public class GoodsInfo extends ezs_goods{
	
	private ezs_accessory mainPhoto;
	
	public ezs_accessory getMainPhoto() {
		return mainPhoto;
	}
	public void setMainPhoto(ezs_accessory mainPhoto) {
		this.mainPhoto = mainPhoto;
	}
	public GoodsInfo(ezs_accessory mainPhoto) {
		super();
		this.mainPhoto = mainPhoto;
	}
	public GoodsInfo() {
		super();
	}
}
