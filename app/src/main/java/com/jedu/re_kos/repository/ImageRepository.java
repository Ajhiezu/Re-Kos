package com.jedu.re_kos.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.network.ApiService;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    public void uploadImage(File imageFile, String userId, UploadCallback callback) {
        // Prepare data and call API to upload image
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), requestBody);
        RequestBody userIdPart = RequestBody.create(MediaType.parse("text/plain"), userId);

        apiService.uploadImage(imagePart, userIdPart).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    // Callback interfaces for success/failure handling
    public interface ImageCallback {
        void onSuccess(Bitmap bitmap);
        void onError(String errorMessage);
    }

    public interface UploadCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
}
