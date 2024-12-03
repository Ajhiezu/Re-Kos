package com.jedu.re_kos.Model.request;

public class UpdateRequest {
    private String name;

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    private int id_user;

    public UpdateRequest(int id_user, String name, String inputGender, String customDate, String pekerjaan, String inputInstansi, String alamat, String noTelp) {
        this.id_user = id_user;
        this.name = name;
        this.inputGender = inputGender;
        this.customDate = customDate;
        this.pekerjaan = pekerjaan;
        this.inputInstansi = inputInstansi;
        this.alamat = alamat;
        this.noTelp = noTelp;
    }

    private String inputGender;
    private String customDate;
    private String pekerjaan;
    private String inputInstansi;
    private String alamat;
    private String noTelp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInputGender() {
        return inputGender;
    }

    public void setInputGender(String inputGender) {
        this.inputGender = inputGender;
    }

    public String getCustomDate() {
        return customDate;
    }

    public void setCustomDate(String customDate) {
        this.customDate = customDate;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getInputInstansi() {
        return inputInstansi;
    }

    public void setInputInstansi(String inputInstansi) {
        this.inputInstansi = inputInstansi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

}
