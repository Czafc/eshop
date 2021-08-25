package com.czaeshop.pojo;

import java.util.List;

public class Order {
    private String orderId; //订单Id
    private String userId; //订单所有者的用户Id
    private int orderStatus; //订单状态
    private int totalProductNum; //商品总数
    private double totalPrice; //订单价格
    private String createTime; // 订单创建时间

    private List<OrderItem> orderItems; //订单中的商品信息

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getTotalProductNum() {
        return totalProductNum;
    }

    public void setTotalProductNum(int totalProductNum) {
        this.totalProductNum = totalProductNum;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
