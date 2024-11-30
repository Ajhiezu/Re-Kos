package com.jedu.re_kos.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.model.KosModel;
import com.jedu.re_kos.repository.KosRepository;

import java.util.List;

public class KosViewModel extends ViewModel {
    private final KosRepository repository;
    private final MutableLiveData<List<KosModel.KostData>> liveDataKos, liveDataBestKos, liveDataTerdekat;

    public KosViewModel() {
        repository = new KosRepository();
        liveDataKos = repository.DataKos();
        liveDataBestKos = repository.DataBestKos();
        liveDataTerdekat = repository.DataTerdekatKos();
    }

    public LiveData<List<KosModel.KostData>> getKosLiveData() {
        return liveDataKos;
    }
    public LiveData<List<KosModel.KostData>> getKosBestLiveData() {
        return liveDataBestKos;
    }
    public LiveData<List<KosModel.KostData>> getKosTerdekatLiveData() {
        return liveDataTerdekat;
    }
    public LiveData<List<KosModel.KostData>> getDataAllKosLiveData(String lokasi, String harga) {
        Log.d("asd", "Lokasi: " +lokasi);
        return repository.DataAllKos(lokasi,harga);
    }
}