package com.lznby.jetpack.content.design.params;

/**
 * register params
 * @author Lznby
 */
public class RegisterParams {

    String userNickName;
    String userPassword;

    public RegisterParams() {
    }

    public RegisterParams(String userNickName, String userPassword) {
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
