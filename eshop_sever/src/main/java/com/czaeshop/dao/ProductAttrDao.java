package com.czaeshop.dao;

import com.czaeshop.pojo.ProductAttribute;

import java.util.List;

public interface ProductAttrDao {
    List<ProductAttribute> getAttributesByProductId(int productId);
}
