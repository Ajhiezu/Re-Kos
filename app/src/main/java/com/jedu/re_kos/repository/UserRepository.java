package com.jedu.re_kos.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.model.request.UpdateRequest;
import com.jedu.re_kos.model.response.UpdateRespon;
import com.jedu.re_kos.model.response.UserResponse;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;

    public UserRepository() {
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<UserResponse> getData(int id) {
        MutableLiveData<UserResponse> userResponse = new MutableLiveData<>();

        apiService.getDataById(id).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()){
                    userResponse.setValue(response.body());
                } else {
                    handleErrorResponse(response, userResponse);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
        return userResponse;
    }

    public LiveData<UpdateRespon> updateUser(UpdateRequest updateRequest) {
        MutableLiveData<UpdateRespon> updateRequestMutableLiveData = new MutableLiveData<>();
        apiService.updateUser(updateRequest).enqueue(new Callback<UpdateRespon>() {
            @Override
            public void onResponse(Call<UpdateRespon> call, Response<UpdateRespon> response) {
                if (response.isSuccessful()) {
                   updateRequestMutableLiveData.postValue(response.body());
                } else {
                    Log.e("API Error", "Error response: " + response.errorBody().toString());
//                    handleErrorResponse(response, userResponse);
                }
            }

            @Override
            public void onFailure(Call<UpdateRespon> call, Throwable t) {
//                userResponse.postValue(null); // Handle failure
                Log.e("UpdateUser", "Gagal mengupdate data", t);
            }
        });
        return updateRequestMutableLiveData;
    }

    private void handleErrorResponse(Response<UserResponse> response, MutableLiveData<UserResponse> userResponse) {
        try {
            String errorBody = response.errorBody().string();
            // Log the raw error body
            Log.e("API_ERROR", "Error body: " + errorBody);

            // Check if the errorBody is a valid JSON
            if (isValidJson(errorBody)) {
                // Handle valid JSON error response
                UserResponse errorResponse = new UserResponse();
                errorResponse.setStatus("error");
                errorResponse.setMessage("Error: " + new JSONObject(errorBody).optString("message"));
                userResponse.setValue(errorResponse);
            } else {
                // Handle unexpected format (e.g., plain string)
                UserResponse errorResponse = new UserResponse();
                errorResponse.setStatus("error");
                errorResponse.setMessage("Unexpected error: " + errorBody);
                userResponse.setValue(errorResponse);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            userResponse.setValue(null);
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
