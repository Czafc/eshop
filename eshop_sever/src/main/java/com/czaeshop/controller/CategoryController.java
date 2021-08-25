package com.czaeshop.controller;

import com.alibaba.fastjson.JSON;
import com.czaeshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/categories",produces = {"application/json;charset=UTF-8"})
    public String getCategories(){
        System.out.println("/categoryController/categories called");
        String res = JSON.toJSONString(categoryService.getAllCategories());
        return res;
    }
}
