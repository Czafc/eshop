package com.czaeshop.controller;

import com.alibaba.fastjson.JSON;
import com.czaeshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    //将商品添加进购物车
    @RequestMapping(value = "/addToCart", method = RequestMethod.POST,consumes={ "application/json" },produces={ "application/json" })
    public String addProductToCart(@RequestBody String cartJson) {
        System.out.println("/cart/addToCart called and data is :" + cartJson);
        String resStr;
        @SuppressWarnings("unchecked")
        Map<String,Object> cart = (Map<String,Object>)JSON.parse(cartJson);
        if(cartService.addProductToCart((Integer)cart.get("productId"), (String)cart.get("userId"), (Integer)cart.get("num"))) {
            resStr = "success";
        }else{
            resStr =  "fail";
        }
        return resStr;
    }

    //获取用户购物车中的所有商品数据
    @RequestMapping(
            value = "/myCart",
            method = RequestMethod.GET,
            produces = {"application/json;charset=UTF-8"})
    public String getCartOfUser(@RequestParam("userId") String userId) {
        System.out.println("/myCart?userId="+ userId +" called");
        return JSON.toJSONString(cartService.getCartOfUser(userId));
    }


    // 更新用户购物车数据
    @RequestMapping(value = "/updateCart", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public String updateCartOfUser(@RequestBody String cartJson) {
        System.out.println("/updateCart called");
        //将json字符串转化成map列表
        @SuppressWarnings("unchecked")
        List<Map<String,Object> > carts = (List<Map<String,Object>>)JSON.parse(cartJson);
        if(cartService.updateCartOfUser(carts)) {
            return "success";
        }else {
            return "fail";
        }
    }

}
