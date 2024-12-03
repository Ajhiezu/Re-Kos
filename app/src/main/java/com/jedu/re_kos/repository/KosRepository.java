package com.jedu.re_kos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class KosRepository {
    private final ApiService apiService;

    public KosRepository() {
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<KosModel.KostData>> DataKos() {
        MutableLiveData<List<KosModel.KostData>> liveDataKos = new MutableLiveData<>();

        apiService.kos().enqueue(new Callback<KosModel>() {
            @Override
            public void onResponse(Call<KosModel> call, Response<KosModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveDataKos.postValue(response.body().getData()); // Set data ke LiveData
                } else {
                    liveDataKos.postValue(new ArrayList<>()); // Data kosong jika response gagal
                }
            }

            @Override
            public void onFailure(Call<KosModel> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
                liveDataKos.postValue(new ArrayList<>()); // Data kosong jika API gagal
            }
        });

        return liveDataKos;
    }
}
