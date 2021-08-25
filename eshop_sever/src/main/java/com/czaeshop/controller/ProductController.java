package com.czaeshop.controller;


import com.alibaba.fastjson.JSON;
import com.czaeshop.pojo.Product;
import com.czaeshop.pojo.ProductList;
import com.czaeshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/search", produces = {"application/json;charset=UTF-8"},method = RequestMethod.GET)
    public String getProductList(
            @RequestParam(required = false, value = "cid") Integer catId,
            @RequestParam(required = false, value = "query") String name,
            @RequestParam(required = false, value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(required = false, value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(required = false, value = "sort", defaultValue = "0") int sort
    ){
        System.out.println("/search called");
        ProductList productList = null;
        if(null != catId){
            productList = productService.queryProductList(catId, pageSize, pageNum, sort);
        }else if(null != name && !"".equals(name)) {
            productList = productService.queryProductList(name, pageSize, pageNum, sort);
        }
        String resString  = JSON.toJSONString(productList);
        System.out.println(resString);
        return JSON.toJSONString(productList);
    }

    //获取商品详细信息
    @RequestMapping(value = "/productDetail", produces = {"application/json;charset=UTF-8"}, method = RequestMethod.GET)
    public String getProductInfoDetail(
            @RequestParam(value = "productId") int productId,
            @RequestParam(value = "userId", required = false) String userId
    ){
        System.out.println("productDetail called");
        Product product = null;
        if(null != userId && !"".equals(userId)) {
            product = productService.getProductInfo(productId, userId);
        }else {
            product = productService.getProductInfo(productId);
        }
        return JSON.toJSONString(product);
    }
}
