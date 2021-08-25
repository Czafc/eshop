package com.czaeshop.serviceImpl;

import com.czaeshop.pojo.ServerName;
import com.czaeshop.dao.CarouselPicDao;
import com.czaeshop.pojo.CarouselPic;
import com.czaeshop.service.CarouselPicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("CarouselPicService")
public class CarouselPicServiceImpl implements CarouselPicService {
    @Resource
    private CarouselPicDao carouselPicDao;

    @Override
    public List<CarouselPic> queryCarouselPics() {
        List<CarouselPic> carouselPics = carouselPicDao.queryCarouselPics();
        for(CarouselPic carouselPic : carouselPics) {
            carouselPic.setImgURL(ServerName.access().getServerName() + carouselPic.getImgURL());
        }
        return carouselPics;
    }
}
