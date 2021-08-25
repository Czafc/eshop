package com.czaeshop.serviceImpl;

import com.czaeshop.dao.CartDao;
import com.czaeshop.pojo.CartItem;
import com.czaeshop.pojo.ServerName;
import com.czaeshop.service.CartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("CartService")
public class CartServiceImpl implements CartService {
    @Resource
    CartDao cartDao;

    @Override
    public boolean addProductToCart(int productId, String userId, int num) {
        int rowCount = cartDao.findRow(productId,userId);
        try{
            if(rowCount == 0) {
                cartDao.insertRowToCart(productId, userId, num);
            }else {
                cartDao.addProductNumOfCart(productId, userId, num);
            }
        }catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public List<CartItem> getCartOfUser(String userId) {
        List<CartItem> cartItemList = cartDao.getCartOfUser(userId);
        for(CartItem cartItem : cartItemList) {
            cartItem.setCoverImg(ServerName.access().getServerName() + cartItem.getCoverImg());
        }
        return cartItemList;
    }

    @Override
    public boolean updateCartOfUser(List<Map<String, Object>> carts) {
        for(Map<String, Object> cart : carts) {
            try {
                if((Integer)cart.get("changed") == 1) {
                    //商品修改状态为“已修改”，更新商品数量
                    cartDao.updateCart((Integer)cart.get("cartId"), (Integer)cart.get("productNum"));
                }else if((Integer)cart.get("changed") == 2) {
                    //商品修改状态为“已删除/结算”，将购物车项删除
                    cartDao.deleteRowFromCart((Integer)cart.get("cartId"));
                }
            }catch (Exception e) {
                System.out.println("=====updating cart " + cart.get("cartId") + " error=====");
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
