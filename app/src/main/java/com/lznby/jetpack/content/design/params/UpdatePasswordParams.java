package com.lznby.jetpack.content.design.params;

/**
 * @author Lznby
 */
public class UpdatePasswordParams {
    private String userPassword;
    private String newUserPassword;
    private String rUserPassword;

    public UpdatePasswordParams() {
    }

    public UpdatePasswordParams(String userPassword, String newUserPassword, String rUserPassword) {
        this.userPassword = userPassword;
        this.newUserPassword = newUserPassword;
        this.rUserPassword = rUserPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getNewUserPassword() {
        return newUserPassword;
    }

    public void setNewUserPassword(String newUserPassword) {
        this.newUserPassword = newUserPassword;
    }

    public String getrUserPassword() {
        return rUserPassword;
    }

    public void setrUserPassword(String rUserPassword) {
        this.rUserPassword = rUserPassword;
    }
}
