package com.ting.honormanage.model;

import org.springframework.stereotype.Component;

@Component
public class ChangePasswordModel {
    private String oldPassword;

    private String newPassword;

    public ChangePasswordModel() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
