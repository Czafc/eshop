package com.czaeshop.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    void registNewUser(String userId); //添加新的用户
    int isUserExist(String userId); //判断用户是否存在
}
