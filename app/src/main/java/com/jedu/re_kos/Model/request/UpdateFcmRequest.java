package com.jedu.re_kos.Model.request;

public class UpdateFcmRequest {
    private int id_user;
    private String fcm_token;

    public UpdateFcmRequest(int id_user, String fcm_token) {
        this.id_user = id_user;
        this.fcm_token = fcm_token;
    }

    // Getter dan Setter
    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int id_user) {
        this.id_user = id_user;
    }

    public String getFcmToken() {
        return fcm_token;
    }

    public void setFcmToken(String fcm_token) {
        this.fcm_token = fcm_token;
    }
}