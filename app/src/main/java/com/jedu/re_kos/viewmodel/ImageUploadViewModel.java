package com.jedu.re_kos.viewmodel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.model.ProfileImageResponse;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;
import com.jedu.re_kos.repository.ImageRepository;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadViewModel extends ViewModel {
    private final ImageRepository imageRepository;
    private final MutableLiveData<ProfileImageResponse> imageData = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> uploadStatus = new MutableLiveData<>();

    public ImageUploadViewModel(ImageRepository repository) {
        this.imageRepository = repository;
    }

    public LiveData<ProfileImageResponse> getImageData() {
        return imageData;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getUploadStatus() {
        return uploadStatus;
    }

    public void fetchImage(String userId) {
        ApiService apiService = RetrofitInstance.createService(ApiService.class);
        apiService.getUserProfileImage(userId).enqueue(new Callback<ProfileImageResponse>() {
            @Override
            public void onResponse(Call<ProfileImageResponse> call, Response<ProfileImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    imageData.setValue(response.body());
                } else {
                    error.setValue("Failed to fetch image from server");
                }
            }

            @Override
            public void onFailure(Call<ProfileImageResponse> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    public void uploadProfileImage(Uri imageUri, String userId) {
        ApiService apiService = RetrofitInstance.createService(ApiService.class);

        // Prepare file part
        File file = new File(imageUri.getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        // Prepare user ID part
        RequestBody userIdPart = RequestBody.create(MultipartBody.FORM, userId);

        // Make the network call
        apiService.uploadImage(userIdPart, body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    uploadStatus.setValue(true);
                } else {
                    uploadStatus.setValue(false);
                    error.setValue("Upload failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                uploadStatus.setValue(false);
                error.setValue(t.getMessage());
            }
        });
    }
}
