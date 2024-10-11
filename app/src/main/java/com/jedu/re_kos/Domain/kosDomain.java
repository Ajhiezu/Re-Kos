package com.jedu.re_kos.Domain;

public class kosDomain {
    private String Image;

    public kosDomain(String image, String tagkos, String namaKos, String lokasi, String fasilitas, double rating, double harga) {
        Image = image;
        Tagkos = tagkos;
        NamaKos = namaKos;
        Lokasi = lokasi;
        Fasilitas = fasilitas;
        this.rating = rating;
        this.harga = harga;
    }

    private String Tagkos;
    private  String NamaKos;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTagkos() {
        return Tagkos;
    }

    public void setTagkos(String tagkos) {
        Tagkos = tagkos;
    }

    public String getNamaKos() {
        return NamaKos;
    }

    public void setNamaKos(String namaKos) {
        NamaKos = namaKos;
    }

    public String getLokasi() {
        return Lokasi;
    }

    public void setLokasi(String lokasi) {
        Lokasi = lokasi;
    }

    public String getFasilitas() {
        return Fasilitas;
    }

    public void setFasilitas(String fasilitas) {
        Fasilitas = fasilitas;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    private String Lokasi;
    private  String Fasilitas;
    private  double rating;
    private  double harga;
}
