package com.czaeshop.service;

import com.czaeshop.pojo.Product;
import com.czaeshop.pojo.ProductList;

public interface ProductService {
    ProductList queryProductList(int catId,int pageSize,int pageNum,int sort);
    ProductList queryProductList(String name,int pageSize,int pageNum, int sort);
    Product getProductInfo(int productId, String userId);
    Product getProductInfo(int productId);
}
