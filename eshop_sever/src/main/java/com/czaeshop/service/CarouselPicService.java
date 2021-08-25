package com.czaeshop.service;

import com.czaeshop.pojo.CarouselPic;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 获取主页轮播图
 * */
public interface CarouselPicService {
    List<CarouselPic> queryCarouselPics();
}
