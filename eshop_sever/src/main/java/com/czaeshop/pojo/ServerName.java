package com.czaeshop.pojo;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;
/**
 * 单例  服务器名称
 * */
public class ServerName {
    private String serverName = null;
    private static ServerName thisObj = null;
    private ServerName(){}

    static {
        thisObj = new ServerName();
        String fileName = "serverName.properties";
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties(fileName);
            thisObj.setServerName(properties.get("serverName").toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ServerName access() {
        return thisObj;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}