package com.jedu.re_kos.model;

import com.google.gson.annotations.SerializedName;

public class ProfileImageResponse {
    @SerializedName("imageUrl")
    private String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
