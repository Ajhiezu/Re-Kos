package com.jedu.re_kos.model.response;

import com.google.gson.annotations.SerializedName;
import com.jedu.re_kos.model.UserModel;

public class UserResponse {
    private String status;
    private String message;

    @SerializedName("data")
    private UserModel userModel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
