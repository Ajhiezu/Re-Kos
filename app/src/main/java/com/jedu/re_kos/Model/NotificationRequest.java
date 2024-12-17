package com.jedu.re_kos.Model;

public class NotificationRequest {
    private String token;
    private int harga;

    public NotificationRequest(String token, int harga) {
        this.token = token;
        this.harga = harga;
    }

    public String getToken() {
        return token;
    }

    public int getHarga() {
        return harga;
    }
}
