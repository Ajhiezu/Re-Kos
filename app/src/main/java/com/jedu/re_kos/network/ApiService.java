package com.jedu.re_kos.network;

import com.jedu.re_kos.model.DataModel;
import com.jedu.re_kos.model.LoginRequest;
import com.jedu.re_kos.model.LoginResponse;
import com.jedu.re_kos.model.ProfileImageResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    @GET("data/{id}")
    Call<DataModel> getDataById(@Path("id") int id);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @FormUrlEncoded
    @POST("getImageFile") // Replace with the actual endpoint
    Call<ProfileImageResponse> getUserProfileImage(@Path("userId") String userId);

    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadImage(
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part file
    );
}
