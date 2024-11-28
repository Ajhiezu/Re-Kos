package com.jedu.re_kos.network;

import com.jedu.re_kos.Model.KosModel;
import com.jedu.re_kos.Model.DataModel;
import com.jedu.re_kos.Model.LoginRequest;
import com.jedu.re_kos.Model.LoginResponse;

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
    @GET("kos")Call<KosModel> kos();
    @GET("best")Call<KosModel> bestkos();
    @GET("terdekat")Call<KosModel> kosterdekat();
    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("imgProfile/{userId}") // Replace with the actual endpoint
    Call<ResponseBody> getUserProfileImage(@Path("userId") String userId);

    @Multipart
    @POST("upload") // Adjust this endpoint as needed
    Call<ResponseBody> uploadImage(
            @Part MultipartBody.Part file,
            @Part("user_id") RequestBody userId
    );

    @FormUrlEncoded
    @POST("allkos")
    Call<KosModel> AllKos(@Field("lokasi") String lokasi, @Field("hargaawal") String harga_awal, @Field("hargaakhir") String harga_akhir);
}
