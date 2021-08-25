package com.czaeshop.dao;

import com.czaeshop.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao {
    List<Product> queryProductListByCatId(@Param("catId") int catId, @Param("from") int form, @Param("to") int to);//根据商品类别id查询商品列表
    List<Product> queryProductListByCatIdSortByPrice(@Param("catId") int catId, @Param("from") int form, @Param("to") int to);
    List<Product> queryProductListByCatIdSortBySaleNum(@Param("catId") int catId, @Param("from") int form, @Param("to") int to);

    List<Product> queryProductListByName(@Param("name") String name, @Param("from") int from, @Param("to") int to); //根据商品名称查询商品列表
    List<Product> queryProductListByNameSortByPrice(@Param("name") String name,@Param("from") int from,@Param("to") int to);
    List<Product> queryProductListByNameSortBySaleNum(@Param("name") String name,@Param("from") int from,@Param("to") int to);

    //获取商品数目
    int getProductNumByCatId(int catId);
    int getProductNumByName(String name);

    Product queryProductByProductId(int productId); //根据商品id获取商品详细信息

    void updateSaleNum(String orderId); // 更新产品的销量
}
