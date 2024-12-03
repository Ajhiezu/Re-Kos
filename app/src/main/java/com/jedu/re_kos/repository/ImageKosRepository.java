package com.jedu.re_kos.repository;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.Model.ImageKosResponse;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageKosRepository {
    private final ApiService apiService;

    public ImageKosRepository(){
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public LiveData<ImageKosResponse> fetchImage(String id){
        MutableLiveData<ImageKosResponse> imageData = new MutableLiveData<>();
        apiService.getImageKos(id).enqueue(new Callback<ImageKosResponse>() {
            @Override
            public void onResponse(Call<ImageKosResponse> call, Response<ImageKosResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    imageData.setValue(response.body());
                }else{
                    imageData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ImageKosResponse> call, Throwable t) {
                imageData.setValue(null);
            }
        });
        return imageData;
    }

    public interface ImageCallback {
        void onSuccess(Bitmap bitmap);
        void onError(String errorMessage);
    }

}
