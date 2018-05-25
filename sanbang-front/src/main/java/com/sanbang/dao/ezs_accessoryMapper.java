package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_accessory;
import com.sanbang.vo.userauth.AuthImageVo;

@Repository
public interface ezs_accessoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_accessory record);

    int insertSelective(ezs_accessory record);

    ezs_accessory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_accessory record);

    int updateByPrimaryKey(ezs_accessory record);

	List<ezs_accessory> selectPhotoById(Long photo_id);
	
	List<AuthImageVo> getAuthImgListByStoreid(@Param("storeid")long storeid);
}