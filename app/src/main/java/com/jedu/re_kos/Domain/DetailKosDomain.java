package com.jedu.re_kos.Domain;

public class DetailKosDomain {

    public DetailKosDomain(String imageDetailKos, String namaFasilitas) {
        this.imageDetailKos = imageDetailKos;
        NamaFasilitas = namaFasilitas;
    }

    private String imageDetailKos;

    public String getNamaFasilitas() {
        return NamaFasilitas;
    }

    public void setNamaFasilitas(String namaFasilitas) {
        NamaFasilitas = namaFasilitas;
    }

    public String getImageDetailKos() {
        return imageDetailKos;
    }

    public void setImageDetailKos(String imageDetailKos) {
        this.imageDetailKos = imageDetailKos;
    }

    private String NamaFasilitas;
}
