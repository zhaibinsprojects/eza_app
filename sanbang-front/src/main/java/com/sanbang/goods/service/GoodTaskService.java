package com.sanbang.goods.service;

import com.sanbang.utils.Result;

public interface GoodTaskService {

	 /**
	  * 同步商品库存
	  */
	 public void syncGoodsInventory();
	 /**
	    * 修改真实库存
	  * @return
	  */
	 public Result updateGoodsInventory(Result result,String good_no);

}
