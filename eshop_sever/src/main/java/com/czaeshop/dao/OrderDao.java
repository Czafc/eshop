package com.czaeshop.dao;

import com.czaeshop.pojo.Order;
import com.czaeshop.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao {
    void creatAnOrder(
            @Param("orderId")String orderId,
            @Param("userId") String userId,
            @Param("totalProductNum")int totalProductNum,
            @Param("totalPrice") int totalPrice,
            @Param("consignee") String consignee,
            @Param("address") String address,
            @Param("telephoneNum") String telephoneNum,
            @Param("createTime") String createTime
    );

    void insertItemOfOrder(
            @Param("orderId") String orderId,
            @Param("productId") int productId,
            @Param("productNum") int productNum
    );

    void confirmPay(String orderId);
    List<Order> getOrderListOfUserByStatus(@Param("orderStatus")int orderStatus, @Param("userId") String userId);
    List<Order> getAllOrdersOfUser(String userId);

    Order getOrderByOrderId(String orderId);
    List<OrderItem> getOrderItemsOfOrder(String orderId);

    void deleteOrderRecord(String orderId);
    void deleteOrderItems(String orderId);
}
