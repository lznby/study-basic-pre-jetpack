package com.lznby.jetpack.content.design.params;

/**
 * login params
 * @author Lznby
 */
public class LoginParams {
    String userNickName;
    String userPassword;

    public LoginParams() {
    }

    public LoginParams(String userNickName, String userPassword) {
        this.userNickName = userNickName;
        this.userPassword = userPassword;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
