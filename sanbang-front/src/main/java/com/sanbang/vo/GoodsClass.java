package com.sanbang.vo;


public class GoodsClass {
	private String secondName;	//二级分类名称
	private Long secondId;	//二级分类id
	private String thirdName;	//三级分类名称
	private Long thirdId;	//三级分类id
	
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	public Long getSecondId() {
		return secondId;
	}
	public void setSecondId(Long secondId) {
		this.secondId = secondId;
	}
	public String getThirdName() {
		return thirdName;
	}
	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}
	public Long getThirdId() {
		return thirdId;
	}
	public void setThirdId(Long thirdId) {
		this.thirdId = thirdId;
	}
	
}
