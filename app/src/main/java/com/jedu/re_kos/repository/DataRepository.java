package com.jedu.re_kos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.Model.LoginRequest;
import com.jedu.re_kos.Model.LoginResponse;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private final ApiService apiService;

    public DataRepository() {
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    //login
    public MutableLiveData<LoginResponse> login(String email, String password) {
        MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
        LoginRequest loginRequest = new LoginRequest(email, password);

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    loginResponse.setValue(response.body());
                } else {
                    // Handle error response
                    handleErrorResponse(response, loginResponse);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
                loginResponse.setValue(null);
            }
        });

        return loginResponse;
    }

    private void handleErrorResponse(Response<LoginResponse> response, MutableLiveData<LoginResponse> loginResponse) {
        try {
            String errorBody = response.errorBody().string();
            // Log the raw error body
            Log.e("API_ERROR", "Error body: " + errorBody);

            // Check if the errorBody is a valid JSON
            if (isValidJson(errorBody)) {
                // Handle valid JSON error response
                LoginResponse errorResponse = new LoginResponse();
                errorResponse.setStatus("error");
                errorResponse.setMessage("Error: " + new JSONObject(errorBody).optString("message"));
                loginResponse.setValue(errorResponse);
            } else {
                // Handle unexpected format (e.g., plain string)
                LoginResponse errorResponse = new LoginResponse();
                errorResponse.setStatus("error");
                errorResponse.setMessage("Unexpected error: " + errorBody);
                loginResponse.setValue(errorResponse);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            loginResponse.setValue(null);
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
