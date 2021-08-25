package com.czaeshop.dao;

import com.czaeshop.pojo.CartItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao {
    int findRow(@Param("productId") int productId, @Param("userId") String userId); //查找记录，用于判断数据库中是否存在
    void insertRowToCart(@Param("productId")int productId, @Param("userId")String userId, @Param("num")int num);
    void addProductNumOfCart(@Param("productId")int productId, @Param("userId")String userId, @Param("num")int num);

    List<CartItem> getCartOfUser(String userId);

    //更新购物车中的商品数量
    void updateCart(@Param("cartId") int cartId, @Param("productNum") int productNum);


    //删除一条记录，具体应用时再实现
    void deleteRowFromCart(int cartId);
}
