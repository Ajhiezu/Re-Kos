package com.jedu.re_kos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.model.KosModel;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KosRepository {
    private final ApiService apiService;

    public KosRepository() {
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }
    public MutableLiveData<KosModel.KostData> DataKos() {
        MutableLiveData<KosModel.KostData> KosModel = new MutableLiveData<>();

        apiService.kos().enqueue(new Callback<KosModel.KostData>() {
            @Override
            public void onResponse(Call<KosModel.KostData> call, Response<KosModel.KostData> response) {

                if (response.isSuccessful()) {
                    Log.e("testing","sd");
                    KosModel.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<KosModel.KostData> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
                KosModel.setValue(null);
            }
        });

        return KosModel;
    }
}
