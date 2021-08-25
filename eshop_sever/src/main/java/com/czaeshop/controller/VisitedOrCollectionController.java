package com.czaeshop.controller;

import com.alibaba.fastjson.JSON;
import com.czaeshop.service.VisitedOrCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class VisitedOrCollectionController {
    @Autowired
    VisitedOrCollectionService visitedOrCollectionService;

    //获取所有记录
    @RequestMapping(value = "/getAll", method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    public String getAllRecords(@RequestParam("type")int type, @RequestParam("userId")String userId) {
        System.out.println("/getAllRecords called");
        return JSON.toJSONString(visitedOrCollectionService.getAllRecords(type,userId));
    }

    //新建一条记录
    @RequestMapping(value = "/add", method = RequestMethod.POST,produces = {"application/json"})
    public String addOneRecord(@RequestBody String paramString){
        System.out.println("/addRecord called");
        @SuppressWarnings("unchecked")
        Map<String,Object> map = (Map<String,Object>)JSON.parse(paramString);
        Map<String,String> res = new HashMap<>();
        res.put("result",visitedOrCollectionService.createNewRecord(map));
        return JSON.toJSONString(res);
    }

    //删除一条记录
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteRecord(@RequestParam("type") int type, @RequestParam("itemId") int itemId) {
        System.out.println("/deleteRecord called");
        Map<String,String> res = new HashMap<>();
        res.put("result",visitedOrCollectionService.deleteRecord(type, itemId));
        return JSON.toJSONString(res);
    }

    // 取消收藏
    @RequestMapping(value = "/unCollect", method = RequestMethod.GET)
    public String unCollectProduct(@RequestParam("userId") String userId, @RequestParam("productId") int productId){
        System.out.println("/unCollect called");
        Map<String,String> res = new HashMap<>();
        res.put("result",visitedOrCollectionService.unCollectProduct(userId, productId));
        return JSON.toJSONString(res);
    }

    @RequestMapping(value = "/clearHistory", method = RequestMethod.GET)
    public String clearVisitedHistory(@RequestParam("userId") String userId) {
        System.out.println("/clearHistory called");
        Map<String,String> res = new HashMap<>();
        res.put("result",visitedOrCollectionService.clearVisitedHistory(userId));
        return JSON.toJSONString(res);
    }
}
