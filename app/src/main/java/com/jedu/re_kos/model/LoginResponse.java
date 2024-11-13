package com.jedu.re_kos.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String status;
    private String message;
    private com.jedu.re_kos.model.DataModel data;

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

    public com.jedu.re_kos.model.DataModel getData() {
        return data;
    }

    public void setData(com.jedu.re_kos.model.DataModel data) {
        this.data = data;
    }
}
