package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.repository.KosRepository;

public class KosViewModel extends ViewModel {
    private final KosRepository repository;
    private MutableLiveData<KosModel.KostData> data;

    public KosViewModel(KosRepository repository) {
        this.repository = repository;
        data = repository.DataKos();
    }

    public LiveData<KosModel.KostData> getKos() {
        return data;

    }
}
