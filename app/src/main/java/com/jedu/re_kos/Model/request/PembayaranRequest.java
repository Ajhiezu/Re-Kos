package com.jedu.re_kos.Model.request;

public class PembayaranRequest {
    private int id_user;
    private int id_kos;
    private int harga;

    public PembayaranRequest(int id_user, int id_kos, int harga, String waktu_penyewaan){//, ImageView buktiPembayaran) {
        this.id_user = id_user;
        this.id_kos = id_kos;
        this.harga = harga;
        this.waktu_penyewaan = waktu_penyewaan;
      //  this.buktiPembayaran = buktiPembayaran;
    }

    private String waktu_penyewaan;
   // private ImageView buktiPembayaran;

    public int getId_kos() {
        return id_kos;
    }

    public void setId_kos(int id_kos) {
        this.id_kos = id_kos;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getWaktu_penyewaan() {
        return waktu_penyewaan;
    }

    public void setWaktu_penyewaan(String waktu_penyewaan) {
        this.waktu_penyewaan = waktu_penyewaan;
    }

//    public ImageView getBuktiPembayaran() {
//        return buktiPembayaran;
//    }
//
//    public void setBuktiPembayaran(ImageView buktiPembayaran) {
//        this.buktiPembayaran = buktiPembayaran;
//    }

}
