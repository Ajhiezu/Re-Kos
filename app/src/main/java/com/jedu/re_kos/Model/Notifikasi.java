package com.jedu.re_kos.Model;

import com.google.gson.annotations.SerializedName;


public class Notifikasi {
    @SerializedName("id_pembayaran")
    private int idPembayaran;

    @SerializedName("id_penyewaan")
    private int idPenyewaan;

    @SerializedName("id_user")
    private int idUser;

    @SerializedName("jumlah_pembayaran")
    private int jumlahPembayaran;

    @SerializedName("sisa_hari")
    private int sisaHari;

    @SerializedName("total_hari")
    private int totalHari;

    @SerializedName("durasi")
    private int durasi;

    @SerializedName("tanggal_pembayaran")
    private String tanggalPembayaran;

    private String tipeNotifikasi;

    // Getter dan Setter

    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getTanggalPembayaran() {
        return tanggalPembayaran;
    }

    public void setTanggalPembayaran(String tanggalPembayaran) {
        this.tanggalPembayaran = tanggalPembayaran;
    }

    public int getIdPenyewaan() {
        return idPenyewaan;
    }

    public void setIdPenyewaan(int idPenyewaan) {
        this.idPenyewaan = idPenyewaan;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getJumlahPembayaran() {
        return jumlahPembayaran;
    }

    public void setJumlahPembayaran(int jumlahPembayaran) {
        this.jumlahPembayaran = jumlahPembayaran;
    }

    public int getSisaHari() {
        return sisaHari;
    }

    public void setSisaHari(int sisaHari) {
        this.sisaHari = sisaHari;
    }

    public int getTotalHari() {
        return totalHari;
    }

    public void setTotalHari(int totalHari) {
        this.totalHari = totalHari;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public String getTipeNotifikasi() {
        return tipeNotifikasi;
    }

    public void setTipeNotifikasi(String tipeNotifikasi) {
        this.tipeNotifikasi = tipeNotifikasi;

    }


}
