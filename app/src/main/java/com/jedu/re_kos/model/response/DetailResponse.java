package com.jedu.re_kos.model.response;

import com.jedu.re_kos.model.DetailModel;

import java.util.List;

public class DetailResponse {
    private String status;
    private String message;
    private List<DetailModel> data;

    // Getter dan Setter untuk status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter dan Setter untuk message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Konstruktor default (opsional)
    public DetailResponse() {}

    // Konstruktor penuh (opsional)
    public DetailResponse(String status, String message, DetailModel detailModel) {
        this.status = status;
        this.message = message;
    }
    public List<DetailModel> getData() {
        return data;
    }

    public void setData(List<DetailModel> data) {
        this.data = data;
    }

    public DetailModel getDetailModel() {
        // Pastikan Anda mengambil elemen pertama dari daftar
        return data != null && !data.isEmpty() ? data.get(0) : null;
    }
}
