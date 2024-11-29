package com.jedu.re_kos.model;

public class DetailModel {
    private int id_kos;
    private String nama_kos;
    private String alamat;
    private Double rating_kamar;
    private int kamar_tersedia;
    private int harga_bulan;

    public int getHarga_minggu() {
        return harga_minggu;
    }

    public void setHarga_minggu(int harga_minggu) {
        this.harga_minggu = harga_minggu;
    }

    public int getId_kos() {
        return id_kos;
    }

    public void setId_kos(int id_kos) {
        this.id_kos = id_kos;
    }

    public String getNama_kos() {
        return nama_kos;
    }

    public void setNama_kos(String nama_kos) {
        this.nama_kos = nama_kos;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getRating_kamar() {
        return rating_kamar;
    }

    public void setRating_kamar(Double rating_kamar) {
        this.rating_kamar = rating_kamar;
    }

    public int getKamar_tersedia() {
        return kamar_tersedia;
    }

    public void setKamar_tersedia(int kamar_tersedia) {
        this.kamar_tersedia = kamar_tersedia;
    }

    public int getHarga_bulan() {
        return harga_bulan;
    }

    public void setHarga_bulan(int harga_bulan) {
        this.harga_bulan = harga_bulan;
    }

    public int getHarga_hari() {
        return harga_hari;
    }

    public void setHarga_hari(int harga_hari) {
        this.harga_hari = harga_hari;
    }

    public String getWaktu_penyewaan() {
        return waktu_penyewaan;
    }

    public void setWaktu_penyewaan(String waktu_penyewaan) {
        this.waktu_penyewaan = waktu_penyewaan;
    }

    public String getJenis_fasilitas() {
        return jenis_fasilitas;
    }

    public void setJenis_fasilitas(String jenis_fasilitas) {
        this.jenis_fasilitas = jenis_fasilitas;
    }

    public String getPeraturan_kos() {
        return peraturan_kos;
    }

    public void setPeraturan_kos(String peraturan_kos) {
        this.peraturan_kos = peraturan_kos;
    }

    public String getFasilitas_kos() {
        return fasilitas_kos;
    }

    public void setFasilitas_kos(String fasilitas_kos) {
        this.fasilitas_kos = fasilitas_kos;
    }

    private int harga_minggu;
    private int harga_hari;
    private String waktu_penyewaan;
    private String jenis_fasilitas;
    private String peraturan_kos;
    private String fasilitas_kos;
}
