package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.repository.KosRepository;

import java.util.List;

public class KosViewModel extends ViewModel {
    private final KosRepository repository;
    private final MutableLiveData<List<KosModel.KostData>> liveDataKos;

    public KosViewModel() {
        repository = new KosRepository();
        liveDataKos = repository.DataKos(); // Mengambil data dari repository
    }

    public LiveData<List<KosModel.KostData>> getKosLiveData() {
        return liveDataKos;
    }
}

