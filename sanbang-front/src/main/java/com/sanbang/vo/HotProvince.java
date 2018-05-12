package com.sanbang.vo;

import java.util.List;

import com.sanbang.bean.ezs_area;

public class HotProvince extends ezs_area {
	private Long id;
	private String areaName;
	private Long num;
	private List<ezs_area> elist;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
	
	public List<ezs_area> getElist() {
		return elist;
	}

	public void setElist(List<ezs_area> elist) {
		this.elist = elist;
	}

	public HotProvince(Long id, String areaName, Long num, List<ezs_area> elist) {
		super();
		this.id = id;
		this.areaName = areaName;
		this.num = num;
		this.elist = elist;
	}

	public HotProvince() {
		super();
	}
}
