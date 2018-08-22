package com.sanbang.vo.hangq;

import java.util.Date;

/**
 * 行情收藏列表
 * @author langjf
 *
 */
public class HangqCollectedVo {
    private Long id;

    private Boolean deleteStatus;

    private Integer give;

    private Integer house;

    private Long ezsSubstance_id;

    private Long user_id;
    
    private String title;//行情文章标题
    
    private Date doctime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getGive() {
        return give;
    }

    public void setGive(Integer give) {
        this.give = give;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public Long getEzsSubstance_id() {
        return ezsSubstance_id;
    }

    public void setEzsSubstance_id(Long ezsSubstance_id) {
        this.ezsSubstance_id = ezsSubstance_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDoctime() {
		return doctime;
	}

	public void setDoctime(Date doctime) {
		this.doctime = doctime;
	}
    
}