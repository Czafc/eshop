package com.czaeshop.dao;

import com.czaeshop.pojo.ProductPicture;

import java.util.List;

public interface ProductPictureDao {
    List<ProductPicture> getPicturesByProductId(int productId);
}
