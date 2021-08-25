package com.czaeshop.controller;

import com.alibaba.fastjson.JSON;
import com.czaeshop.service.CarouselPicService;
import com.czaeshop.service.IndexFloorsService;
import com.czaeshop.service.NavigatorIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {
    @Autowired
    CarouselPicService carouselPicService;

    @Autowired
    NavigatorIconService navigatorIconService;

    @Autowired
    IndexFloorsService indexFloorsService;

    //加载首页轮播图
    @RequestMapping("/carouselPics")
    public String getCarouselPics() {
        System.out.println("carouselPics called");
        String jsonRes = JSON.toJSONString(carouselPicService.queryCarouselPics());
        return jsonRes;
    }

    //加载首页导航菜单
    @RequestMapping(value = "/navigatorIcons",produces = {"application/json;charset=UTF-8"})
    public String getCatItems() {
        System.out.println("navigatorIcons called");
        String jsonRes = JSON.toJSONString(navigatorIconService.getNavigatorIcons());
        return jsonRes;
    }

    //加载首页商品楼层信息
    @RequestMapping(value = "/floors", produces = {"application/json;charset=UTF-8"})
    public String getFloors() {
        System.out.println("floors called");
        String jsonRes = JSON.toJSONString(indexFloorsService.getFloors());
        return jsonRes;
    }

    @RequestMapping("/testInterface")
    public void test() {
        System.out.println("hello world");
    }

    @RequestMapping("/testParam")
    public String testParam(@RequestParam("name") String name, @RequestParam("age") int age) {
        System.out.println(name);
        return age + "-year-old "+name + " hello";
    }
}
