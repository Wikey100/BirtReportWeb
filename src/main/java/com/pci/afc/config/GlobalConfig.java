package com.pci.afc.config;

/**
 * Created by xwj on 2018-08-12.
 */
public class GlobalConfig {

    public GlobalConfig(){
        this.uerId="admin";//默认登录用户
    }
    private static GlobalConfig instance=new GlobalConfig();

    public static GlobalConfig getInstance() {
        if (instance==null)
        {
           instance=new GlobalConfig();
        }
        return instance;
    }

    //登录用户ID
    private String uerId;
    public String getUerId() {
        return uerId;
    }

    public void setUerId(String uerId) {
        this.uerId = uerId;
    }

    //登录用户名称
    private  String userName;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    //登录标识
    private  boolean isLogin;

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
