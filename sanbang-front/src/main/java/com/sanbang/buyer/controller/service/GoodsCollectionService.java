package com.sanbang.buyer.controller.service;

import java.util.Map;

public interface GoodsCollectionService {
	
	public Map<String, Object> getCollectGoodsByUser(Long userId);
	
	public Map<String, Object> addGoodToCollection(Long Id,Long userId);
	
	public Map<String, Object> removeGoodFromCollect(Long gId,Long userId);
	
	public Map<String, Object> addGoodCart(Long gId);
	
	public Map<String, Object> selectPriceChanges(Long gId);

}
