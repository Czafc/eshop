package com.czaeshop.dao;

import com.czaeshop.pojo.VisitedItemOrCollectionItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionDao {
    void insertOneCollectionRecord(@Param("userId") String userId, @Param("productId") int productId);
    List<VisitedItemOrCollectionItem> getAllCollectionRecordsOfUser(String userId);
    void deleteOneCollectionRecord(@Param("itemId")int itemId);
    Integer getCollectionItemId(@Param("productId") int productId, @Param("userId") String userId);
    void unCollectProduct(@Param("productId") int productId, @Param("userId") String userId);
}
