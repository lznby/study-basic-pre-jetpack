package com.lznby.jetpack.content.design.entity;

/**
 * @author Lznby
 */
public class LoginEntity {

    private String token;
    private UserBaseInfoEntity userBaseInfoEntity;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserBaseInfoEntity getUserBaseInfoEntity() {
        return userBaseInfoEntity;
    }

    public void setUserBaseInfoEntity(UserBaseInfoEntity userBaseInfoEntity) {
        this.userBaseInfoEntity = userBaseInfoEntity;
    }
}
