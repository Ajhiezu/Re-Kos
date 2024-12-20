package com.jedu.re_kos.network;

import com.jedu.re_kos.Model.request.UpdateFcmRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("updateFcmToken")
    Call<Void> updateFcmToken(@Body UpdateFcmRequest request);
}
