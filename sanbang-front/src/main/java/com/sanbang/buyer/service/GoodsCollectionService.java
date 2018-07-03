package com.sanbang.buyer.service;

import java.util.Map;

public interface GoodsCollectionService {
	
	public Map<String, Object> getCollectGoodsByUser(Long userId);
	
	public Map<String, Object> removeGoodFromCollect(String[] gids,Long userId);
	
	public Map<String, Object> selectPriceChanges(Long gId);

}
