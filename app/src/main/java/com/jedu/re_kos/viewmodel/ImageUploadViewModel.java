package com.jedu.re_kos.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageUploadViewModel extends ViewModel {
//    private final ImageRepository imageRepository;
    private MutableLiveData<Bitmap> imageData = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> uploadStatus = new MutableLiveData<>();

//    public ImageUploadViewModel(ImageRepository repository) {
//        this.imageRepository = repository;
//    }

    public void fetchImage(String userId) {
        ApiService apiService = RetrofitInstance.createService(ApiService.class);
        apiService.getUserProfileImage(userId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Convert ResponseBody to Bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    imageData.setValue(bitmap);  // Set the Bitmap in LiveData
                } else {
                    error.setValue("Failed to fetch image from server");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    // Getters for LiveData
    public LiveData<Bitmap> getImageData() {
        return imageData;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void uploadImage(File imageFile, int userId) {
        ApiService apiService = RetrofitInstance.createService(ApiService.class);

        // Create RequestBody for the image file and userId
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", imageFile.getName(), requestBody);
        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));

        Call<ResponseBody> call = apiService.uploadImage(part, userIdBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseString);
                        String status = jsonResponse.getString("status");
                        String message = jsonResponse.getString("message");
                        Log.d("Illham", message);
//                        if ("success".equals(status)) {
//                            fetchImage(userId);
//                        } else {
//                            Log.e("UploadError", "Failed to upload image: " + status);
//                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Handle failure
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
            }
        });
    }
}
