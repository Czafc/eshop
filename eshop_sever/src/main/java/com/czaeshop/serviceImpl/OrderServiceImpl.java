package com.czaeshop.serviceImpl;

import com.czaeshop.dao.CartDao;
import com.czaeshop.dao.OrderDao;
import com.czaeshop.dao.ProductDao;
import com.czaeshop.pojo.Order;
import com.czaeshop.pojo.OrderItem;
import com.czaeshop.pojo.ServerName;
import com.czaeshop.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderDao orderDao;

    @Resource
    CartDao cartDao;

    @Resource
    ProductDao productDao;

    @Override
    public String creatAnOrderForUser(Map<String,Object> orderData) {
        // 订单数据准备 ===================================================

        //使用uuid技术为订单创建唯一标识orderId
        String orderId = UUID.randomUUID().toString().replace("-","");

        int totalProductNum = (Integer)orderData.get("totalProductNum");
        int totalPrice = (Integer)orderData.get("totalPrice");
        String userId = (String)orderData.get("userId");
        String createTime = (String)orderData.get("createTime");
        @SuppressWarnings("unchecked")
        Map<String,String> addressMap = (Map<String,String>)orderData.get("address");
        String consignee = addressMap.get("userName");
        String address = addressMap.get("all");
        String telephoneNum = addressMap.get("telNumber");
        //==================================
        // 新建订单
        orderDao.creatAnOrder(orderId,userId,totalProductNum,totalPrice,consignee,address,telephoneNum,createTime);
        @SuppressWarnings("unchecked")
        List<Map<String,Object> > orderItems = (List<Map<String,Object> >)orderData.get("orderItems");

        // 添加订单的商品数据
        for(Map<String,Object> orderItem : orderItems) {
            //将商品填入相应的订单
            orderDao.insertItemOfOrder(orderId, (Integer)orderItem.get("productId"), (Integer)orderItem.get("productNum"));
            // 订单创建成功后，删除相应的购物车项，此时商品的状态应为“订单创建完成，但是未支付”
            cartDao.deleteRowFromCart((Integer)orderItem.get("cartId"));
        }
        return orderId;
    }

    @Override
    public String confirmPay(String orderId) {
        try{
            orderDao.confirmPay(orderId);
            productDao.updateSaleNum(orderId);
        }catch (Exception e) {
            return "fail";
        }
        return "success";
    }

    @Override
    public List<Order> getOrderListOfUser(int type, String userId) {
        if(type == -1) {
            return orderDao.getAllOrdersOfUser(userId);
        }else {
            return orderDao.getOrderListOfUserByStatus(type,userId);
        }
    }

    @Override
    public Order getOrderDetail(String orderId) {
        Order order = orderDao.getOrderByOrderId(orderId);
        List<OrderItem> orderItems = orderDao.getOrderItemsOfOrder(orderId);
        for(OrderItem orderItem : orderItems) {
            orderItem.setCoverImg(ServerName.access().getServerName() + orderItem.getCoverImg());
        }
        order.setOrderItems(orderItems);
        return order;
    }

    //删除订单信息，先删除所有商品信息，再删除订单记录
    @Override
    public String deleteOrderRecord(String orderId) {
        try{
            orderDao.deleteOrderItems(orderId);
            orderDao.deleteOrderRecord(orderId);
        }catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }


}
