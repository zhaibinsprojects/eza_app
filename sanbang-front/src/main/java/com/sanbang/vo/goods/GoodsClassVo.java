package com.sanbang.vo.goods;

import java.math.BigDecimal;
import java.util.List;

public class GoodsClassVo {
    private Long id;

    private String level;

    private String name;

    private Long parent_id;

    private Integer grade;

    private BigDecimal price;
    
    private int ischeck;
    
    List<GoodsClassVo> childclasses;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getIscheck() {
		return ischeck;
	}

	public void setIscheck(int ischeck) {
		this.ischeck = ischeck;
	}

	public List<GoodsClassVo> getChildclasses() {
		return childclasses;
	}

	public void setChildclasses(List<GoodsClassVo> childclasses) {
		this.childclasses = childclasses;
	}
   
}