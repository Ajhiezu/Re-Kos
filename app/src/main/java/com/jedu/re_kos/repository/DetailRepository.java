package com.jedu.re_kos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.model.LoginResponse;
import com.jedu.re_kos.model.response.DetailResponse;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRepository {
    private ApiService apiService;

    public DetailRepository(){
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<DetailResponse> getDetail(int id){
        MutableLiveData<DetailResponse> detailResponse = new MutableLiveData<>();

        apiService.getDetailKos(id).enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if(response.isSuccessful()){
                    detailResponse.setValue(response.body());
                }else {
                    handleErrorResponse(response, detailResponse);
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                detailResponse.setValue(null);
            }
        });
        return detailResponse;
    }

    private void handleErrorResponse(Response<DetailResponse> response, MutableLiveData<DetailResponse> detailResponse) {
        try {
            String errorBody = response.errorBody().string();
            // Log the raw error body
            Log.e("API_ERROR", "Error body: " + errorBody);

            // Check if the errorBody is a valid JSON
            if (isValidJson(errorBody)) {
                // Handle valid JSON error response
                DetailResponse errorResponse = new DetailResponse();
                errorResponse.setStatus("error");
                errorResponse.setMessage("Error: " + new JSONObject(errorBody).optString("message"));
                detailResponse.setValue(errorResponse);
            } else {
                // Handle unexpected format (e.g., plain string)
                DetailResponse errorResponse = new DetailResponse();
                errorResponse.setStatus("error");
                errorResponse.setMessage("Unexpected error: " + errorBody);
                detailResponse.setValue(errorResponse);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            detailResponse.setValue(null);
        }
    }


    // Check if the response is valid JSON
    private boolean isValidJson(String json) {
        try {
            new JSONObject(json);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

}
