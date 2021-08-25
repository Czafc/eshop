package com.czaeshop.dao;

import com.czaeshop.pojo.CarouselPic;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CarouselPicDao {
    List<CarouselPic> queryCarouselPics();
}
