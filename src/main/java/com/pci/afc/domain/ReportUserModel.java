package com.pci.afc.domain;

/**
 * Created by xwj on 2018-08-12.
 */
public class ReportUserModel {
    //用户编号
    private String userID;
    //用户名称
    private String userName;
    //用户密码
    private String passWord;
    //用户卡编号
    private  String cardId;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
