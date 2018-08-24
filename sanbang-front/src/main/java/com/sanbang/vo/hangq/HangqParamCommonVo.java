package com.sanbang.vo.hangq;

/**
 * 行情城市实体
 * 
 * @author langjf
 *
 */
public class HangqParamCommonVo {

	private long hot;
	private long areaid;
	private String areaname;
	
	private long colorid;
	private String colorname;

	private String appayid;
	private String applayName;

	public long getHot() {
		return hot;
	}

	public void setHot(long hot) {
		this.hot = hot;
	}

	public long getAreaid() {
		return areaid;
	}

	public void setAreaid(long areaid) {
		this.areaid = areaid;
	}

	public String getAreaname() {
		return areaname;
	}

	public long getColorid() {
		return colorid;
	}

	public void setColorid(long colorid) {
		this.colorid = colorid;
	}

	public String getColorname() {
		return colorname;
	}

	public void setColorname(String colorname) {
		this.colorname = colorname;
	}

	public String getAppayid() {
		return appayid;
	}

	public void setAppayid(String appayid) {
		this.appayid = appayid;
	}

	public String getApplayName() {
		return applayName;
	}

	public void setApplayName(String applayName) {
		this.applayName = applayName;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

}
