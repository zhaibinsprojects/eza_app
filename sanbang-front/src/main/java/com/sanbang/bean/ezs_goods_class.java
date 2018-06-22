package com.sanbang.bean;

import java.util.Date;
import java.util.List;

public class ezs_goods_class {
    private Long id;

    private Date addTime;

    private Boolean deleteStatus;

    private String level;

    private String name;

    private Integer sequence;

    private Long parent_id;

    private Long photo_id;

    private Boolean display;
    /**
     * add by zhaibin 
     * 商品类别图片
     */
    private ezs_accessory photo;
    /**
     * add by zhaibin
     * 子节点列表
     */
    private List<ezs_goods_class> childNodeList;
    /**
     * add by zhaibin
     * 父节点
     */
    private ezs_goods_class parentNode;
    
     

    public List<ezs_goods_class> getChildNodeList() {
		return childNodeList;
	}

	public void setChildNodeList(List<ezs_goods_class> childNodeList) {
		this.childNodeList = childNodeList;
	}

	public ezs_goods_class getParentNode() {
		return parentNode;
	}

	public void setParentNode(ezs_goods_class parentNode) {
		this.parentNode = parentNode;
	}

	public ezs_accessory getPhoto() {
		return photo;
	}

	public void setPhoto(ezs_accessory photo) {
		this.photo = photo;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
    }

    public Long getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(Long photo_id) {
        this.photo_id = photo_id;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }
}