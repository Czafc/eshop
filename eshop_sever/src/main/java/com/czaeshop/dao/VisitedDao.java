package com.czaeshop.dao;

import com.czaeshop.pojo.VisitedItemOrCollectionItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitedDao {
    void insertOneVisitedRecord(@Param("userId") String userId, @Param("productId") int productId);
    List<VisitedItemOrCollectionItem> getAllVisitedRecordsOfUser(String userId);
    void deleteOneVisitedRecord(@Param("itemId")int itemId);
    void clearVisitedHistory(String userId);
}
