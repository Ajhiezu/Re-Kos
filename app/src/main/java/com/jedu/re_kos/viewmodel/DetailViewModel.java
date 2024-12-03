package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.Model.response.DetailResponse;
import com.jedu.re_kos.Model.response.PembayaranResponse;
import com.jedu.re_kos.repository.DetailRepository;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DetailViewModel extends ViewModel {
    private final DetailRepository detailRepository;

    public DetailViewModel() {
        detailRepository = new DetailRepository();
    }

    public LiveData<DetailResponse> getDetail(int id) {
        return detailRepository.getDetail(id);
    }

    public LiveData<PembayaranResponse> konfirmPay(
            RequestBody idUser,
            RequestBody idKamar,
            RequestBody idKos,
            RequestBody harga,
            RequestBody waktuSewa,
            MultipartBody.Part buktiPembayaran
    ) {
        return detailRepository.konfirmPay(idUser, idKamar,idKos, harga, waktuSewa, buktiPembayaran);
    }

}
