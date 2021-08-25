package com.czaeshop.service;

import com.czaeshop.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {
    String creatAnOrderForUser(Map<String, Object> orderData); //创建订单并返回OrderId
    String confirmPay(String orderId); // 订单支付
    List<Order> getOrderListOfUser(int type, String userId);

    Order getOrderDetail(String orderId);

    String deleteOrderRecord(String orderId);
}
