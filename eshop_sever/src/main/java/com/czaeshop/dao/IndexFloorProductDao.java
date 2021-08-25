package com.czaeshop.dao;

import com.czaeshop.pojo.IndexFloorProduct;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IndexFloorProductDao {
    List<IndexFloorProduct> getProductsOfFloor(int floor);
}
