package com.jedu.re_kos.repository;

import com.jedu.re_kos.model.ProfileImageResponse;
import com.jedu.re_kos.network.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageRepository {
    private final ApiService apiService;

    public ImageRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void fetchImage(String userId, FetchImageCallback callback) {
        Call<ProfileImageResponse> call = apiService.getUserProfileImage(userId);
        call.enqueue(new Callback<ProfileImageResponse>() {
            @Override
            public void onResponse(Call<ProfileImageResponse> call, Response<ProfileImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    imageData.setValue(response.body());
                } else {
//                    error.setValue("Failed to fetch image from server");
                }
            }

            @Override
            public void onFailure(Call<ProfileImageResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public interface FetchImageCallback {
        void onSuccess(ResponseBody imageResponseBody);
        void onError(String errorMessage);
    }
}
