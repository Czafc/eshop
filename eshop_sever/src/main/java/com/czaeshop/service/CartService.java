package com.czaeshop.service;

import com.czaeshop.pojo.CartItem;

import java.util.List;
import java.util.Map;

//购物车业务逻辑接口
public interface CartService {
    boolean addProductToCart(int productId, String userId,int num);
    List<CartItem> getCartOfUser(String userId);
    boolean updateCartOfUser(List<Map<String,Object> > carts);
}
