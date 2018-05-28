package com.sanbang.dao;

import java.util.List;

import com.sanbang.bean.ezs_return_attach;

public interface ezs_return_attachMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_return_attach record);

    int insertSelective(ezs_return_attach record);

    ezs_return_attach selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_return_attach record);

    int updateByPrimaryKey(ezs_return_attach record);
    
    /**
     * 得到退货附件
     * @param returnid
     * @return
     */
    List<ezs_return_attach>  getEzsReturnAttachByReturnId(long returnid);
}