package com.czaeshop.controller;

import com.alibaba.fastjson.JSON;
import com.czaeshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(
            value = "/createOrder",
            method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"},
            consumes = {"application/json;charset=UTF-8"}
    )
    public String createOrderForUser(@RequestBody String orderDataJson){
        System.out.println("createOrder called");
        @SuppressWarnings("unchecked")
        Map<String,Object> orderMap = (Map<String,Object>)JSON.parse(orderDataJson);

        // 结果map
        Map<String,String> resMap = new HashMap<>();
        resMap.put("orderId",orderService.creatAnOrderForUser(orderMap));
        return JSON.toJSONString(resMap);
    }


    @RequestMapping(value = "/confirmPay", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String confirmPay(@RequestParam("orderId") String orderId) {
        System.out.println("/order/confirmPay");
        return orderService.confirmPay(orderId);
    }


    @RequestMapping(value = "/myOrders", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String getOrderListOfUser(@RequestParam("type")int type, @RequestParam("userId")String userId){
        System.out.println("/order/myOrders called");
        return JSON.toJSONString(orderService.getOrderListOfUser(type,userId));
    }

    /**
     * 获取订单详情
     * */
    @RequestMapping(value = "/orderDetail", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String getOrderDetail(@RequestParam("orderId") String orderId) {
        System.out.println("/orderDetail called");
        return JSON.toJSONString(orderService.getOrderDetail(orderId));
    }

    /**
     * 删除订单
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String deleteOrder(@RequestParam("orderId")String orderId) {
        System.out.println("/deleteOrder called");
        Map<String,String> resMap = new HashMap<>();
        resMap.put("delResult",orderService.deleteOrderRecord(orderId));
        return JSON.toJSONString(resMap);
    }
}
