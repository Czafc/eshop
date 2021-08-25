package com.czaeshop.controller;

import com.alibaba.fastjson.JSON;
import com.czaeshop.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
    public String userLogin(@RequestParam("code") String code) {
        Map<String,String> res = new HashMap<>();
        System.out.println("/login?code=" + code + " called");
        String loginRes = loginService.userLogin(code);
        /*
        * reqErr:请求微信api失败
        * codeErr：由于code错误（非法/使用过等）造成数据返回错误，具体原因可以看service输出的错误信息
        * */
        if("codeErr".equals(loginRes) || "reqErr".equals(loginRes)) {
            System.out.println("登录失败，原因为 ：============>" + loginRes);
            res.put("res" , "fail");
        }else {
            @SuppressWarnings("unchecked")
            Map<String,String> map = (Map<String, String>) JSON.parse(loginRes);
            res.put("res","success");
            res.put("openid",map.get("openid"));
        }
        return JSON.toJSONString(res);
    }
}
