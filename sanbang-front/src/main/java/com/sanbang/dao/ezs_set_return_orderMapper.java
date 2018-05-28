package com.sanbang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.sanbang.bean.ezs_set_return_order;
import com.sanbang.vo.returnorder.ReturnOrderVO;

@Repository
public interface ezs_set_return_orderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ezs_set_return_order record);

    int insertSelective(ezs_set_return_order record);

    ezs_set_return_order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ezs_set_return_order record);

    int updateByPrimaryKey(ezs_set_return_order record);
    
    /**
     * 买家退货订单列表
     * @param userid
     * @param totalpage
     * @return
     */
    List<ReturnOrderVO> returnOrderListforBuyer(@Param("userid")long userid,@Param("totalpage")int totalpage);
    
    /**
     * 退货订单详情
     * @param returnid
     * @return
     */
    ReturnOrderVO returnOrderinfoforBuyer(long returnid);
    
    /**
     * 卖家家退货订单列表
     * @param userid
     * @param totalpage
     * @return
     */
    List<ReturnOrderVO>  returnOrderListforSeller(@Param("userid")long userid,@Param("totalpage")int totalpage
    		,@Param("order_type")int order_type,@Param("state2")int state2);
}