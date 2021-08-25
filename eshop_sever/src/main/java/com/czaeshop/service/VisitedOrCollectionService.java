package com.czaeshop.service;

import com.czaeshop.pojo.VisitedItemOrCollectionItem;

import java.util.List;
import java.util.Map;

public interface VisitedOrCollectionService {
    String createNewRecord(Map<String,Object> map);
    String deleteRecord(int type , int itemId);
    List<VisitedItemOrCollectionItem> getAllRecords(int type , String userId);
    String unCollectProduct(String userId, int productId);
    String clearVisitedHistory(String userId);
}
