package com.jedu.re_kos.network;

import com.jedu.re_kos.model.DataModel;
import com.jedu.re_kos.model.LoginRequest;
import com.jedu.re_kos.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("data/{id}")
    Call<DataModel> getDataById(@Path("id") int id);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
