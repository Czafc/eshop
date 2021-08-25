package com.czaeshop.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.czaeshop.dao.UserDao;
import com.czaeshop.service.LoginService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {
    //测试号appId
    private static final String APPID = "wxc9d14ba4728d4b4d";
    private static final String SECRET = "b5bda2aaf895ab2a8ca9e1450bf21dce";

    @Resource
    UserDao userDao;

    @Override
    public String userLogin(String code) {
        String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APPID + "&secret="+ SECRET +
                "&js_code="+code + "&grant_type=authorization_code";
        //服务器请求url所需要的Spring对象
        RestTemplate restTemplate = new RestTemplate();
        //请求URL
        ResponseEntity<String> responseEntity=restTemplate.exchange(URL, HttpMethod.GET,null,String.class);
        if(null != responseEntity && responseEntity.getStatusCode() == HttpStatus.OK) {
            String data = responseEntity.getBody();
            @SuppressWarnings("unchecked")
            Map<String,String> map = (Map<String, String>) JSON.parse(data);
            for(String key : map.keySet()) {
                System.out.println(key + "========>" + map.get(key));
            }
            try{
                if(userDao.isUserExist(map.get("openid")) == 0) {
                    userDao.registNewUser(map.get("openid"));
                }
                //返回json字符串，包括openId和sessionKey
                return data;
            }catch (Exception e) {
                //当map中不存在openId时发生异常，由code错误（不合法/使用过）引起
                return "codeErr";
            }
        }
        //没有获取到响应，请求错误
        return "reqErr";
    }
}
