package com.jedu.re_kos.Model.request;

public class RegisRequest {
    private String username, email, password;
    private int number;

    public RegisRequest(String username, String email, String password, int number) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.number = number;
    }
}
