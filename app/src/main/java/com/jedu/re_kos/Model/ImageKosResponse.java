package com.jedu.re_kos.Model;

import java.util.List;

public class ImageKosResponse {
    private boolean success;
    private List<String> images;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
