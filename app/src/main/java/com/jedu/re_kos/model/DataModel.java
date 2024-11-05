package com.jedu.re_kos.model;

import com.google.gson.annotations.SerializedName;

public class DataModel {
    @SerializedName("email")
    private String email;
    @SerializedName("id")
    private int id;
    @SerializedName("role")
    private String role;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
