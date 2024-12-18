package com.jedu.re_kos.Model;

import com.google.gson.annotations.SerializedName;


public class Notifikasi {
    @SerializedName("harga")
    private int harga;

    @SerializedName("id_penyewaan")
    private int idPenyewaan;

    @SerializedName("id_user")
    private int idUser;

    @SerializedName("jumlah_pembayaran")
    private int jumlahPembayaran;

    @SerializedName("sisa_hari")
    private int sisaHari;

    @SerializedName("durasi")
    private int durasi;

    @SerializedName("tanggal_penyewaan")
    private String tanggal_penyewaan;

    private String tipeNotifikasi;

    // Getter dan Setter

    public int getHarga() {
        return harga;
    }

    public void setHarga(int idPembayaran) {
        this.harga = idPembayaran;
    }

    public String getWaktu_penyewaan() {
        return tanggal_penyewaan;
    }

    public void setWaktu_penyewaan(String tanggalPembayaran) {
        this.tanggal_penyewaan = tanggalPembayaran;
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
