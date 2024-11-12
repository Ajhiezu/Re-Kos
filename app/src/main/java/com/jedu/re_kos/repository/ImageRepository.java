package com.jedu.re_kos.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.network.ApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {
    private final ApiService apiService;
    private final MutableLiveData<Bitmap> imageData = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public ImageRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    // Fetch the image from the API
    public void fetchImage(String userId) {
        apiService.getUserProfileImage(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Convert the ResponseBody to a Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    imageData.setValue(bitmap);
                } else {
                    error.setValue("Failed to fetch image from server");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                error.setValue("Error: " + t.getMessage());
            }
        });
    }

    // Expose LiveData for image and error
    public LiveData<Bitmap> getImageData() {
        return imageData;
    }

    public LiveData<String> getError() {
        return error;
    }
}
