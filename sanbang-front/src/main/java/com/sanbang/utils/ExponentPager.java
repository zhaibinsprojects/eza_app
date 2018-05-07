package com.sanbang.utils;

import java.io.Serializable;

/**
 * 保证金管理模糊查询
 * 
 * @author langjf
 *
 */
public class ExponentPager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//类型
	private  short typeId;
	//地区
	private  short areaId=0;
	
	public short getTypeId() {
		return typeId;
	}
	public void setTypeId(short typeId) {
		this.typeId = typeId;
	}
	public short getAreaId() {
		return areaId;
	}
	public void setAreaId(short areaId) {
		this.areaId = areaId;
	}
	
}
