package com.czaeshop.serviceImpl;

import com.czaeshop.dao.CollectionDao;
import com.czaeshop.dao.VisitedDao;
import com.czaeshop.pojo.ServerName;
import com.czaeshop.pojo.VisitedItemOrCollectionItem;
import com.czaeshop.service.VisitedOrCollectionService;
import org.springframework.stereotype.Service;
import sun.reflect.annotation.ExceptionProxy;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 收藏商品type=1
 * 历史足迹type=3
 */
@Service("VisitedOrCollectionService")
public class VisitedOrCollectionServiceImpl implements VisitedOrCollectionService {

    @Resource
    VisitedDao visitedDao;
    @Resource
    CollectionDao collectionDao;

    @Override
    public String createNewRecord(Map<String,Object> map) {
        int type = (Integer)map.get("type");
        int productId = 0;
        if(map.get("productId") instanceof String) {
            productId = Integer.parseInt((String)map.get("productId"));
        }else if(map.get("productId") instanceof Integer) {
            productId = (Integer)map.get("productId");
        }
        String userId = (String)map.get("userId");
        if(type == 1) {
            try{
                collectionDao.insertOneCollectionRecord(userId,productId);
            }catch (Exception ex) {
                ex.printStackTrace();
                return "fail";
            }
        }else if(type == 3) {
            try{
                visitedDao.insertOneVisitedRecord(userId,productId);
            }catch (Exception ex) {
                ex.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }

    @Override
    public String deleteRecord(int type , int itemId) {
        if(type == 1) {
            try{
                collectionDao.deleteOneCollectionRecord(itemId);
            }catch (Exception ex) {
                ex.printStackTrace();
                return "fail";
            }
        }else if(type == 3){
            try{
                visitedDao.deleteOneVisitedRecord(itemId);
            }catch(Exception ex) {
                ex.printStackTrace();
                return "fail";
            }
        }
        return "success";
    }

    @Override
    public List<VisitedItemOrCollectionItem> getAllRecords(int type ,String userId) {
        List<VisitedItemOrCollectionItem> res = null;
        if(type == 1) {
            res = collectionDao.getAllCollectionRecordsOfUser(userId);
        }else if(type == 3) {
            res = visitedDao.getAllVisitedRecordsOfUser(userId);
        }
        if(null != res) {
            for (VisitedItemOrCollectionItem voc : res) {
                voc.setCoverImg(ServerName.access().getServerName() + voc.getCoverImg());
            }
        }
        return res;
    }

    @Override
    public String unCollectProduct(String userId, int productId) {
        try{
            collectionDao.unCollectProduct(productId,userId);
        }catch (Exception ex) {
            ex.printStackTrace();
            return "fail";
        }
        return "success";
    }

    @Override
    public String clearVisitedHistory(String userId) {
        try{
            visitedDao.clearVisitedHistory(userId);
        }catch(Exception ex) {
            ex.printStackTrace();
            return "fail";
        }
        return "success";
    }
}
