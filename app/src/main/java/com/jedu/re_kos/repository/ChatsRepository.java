package com.jedu.re_kos.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.jedu.re_kos.Model.ChatModel;
import com.jedu.re_kos.network.ApiService;
import com.jedu.re_kos.network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatsRepository {
    private final ApiService apiService;

    public ChatsRepository() {
        apiService = RetrofitInstance.getRetrofitInstance().create(ApiService.class);
    }

    public MutableLiveData<List<ChatModel.ChatsData>> getListChat(int id) {
        MutableLiveData<List<ChatModel.ChatsData>> liveDataKos = new MutableLiveData<>();

        apiService.getChatsList(id).enqueue(new Callback<ChatModel>() {
            @Override
            public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveDataKos.postValue(response.body().getData()); // Set data ke LiveData
                } else {
                    liveDataKos.postValue(new ArrayList<>()); // Data kosong jika response gagal
                }
            }

            @Override
            public void onFailure(Call<ChatModel> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
                liveDataKos.postValue(new ArrayList<>()); // Data kosong jika API gagal
            }
        });

        return liveDataKos;
    }

    public MutableLiveData<List<ChatModel.ChatsData>> getDetailChat(int id_sender, int id_receiver) {
        MutableLiveData<List<ChatModel.ChatsData>> liveDataKos = new MutableLiveData<>();

        apiService.getDetailChat(id_sender, id_receiver).enqueue(new Callback<ChatModel>() {
            @Override
            public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveDataKos.postValue(response.body().getData()); // Set data ke LiveData
                } else {
                    liveDataKos.postValue(new ArrayList<>()); // Data kosong jika response gagal
                }
            }

            @Override
            public void onFailure(Call<ChatModel> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
                liveDataKos.postValue(new ArrayList<>()); // Data kosong jika API gagal
            }
        });

        return liveDataKos;
    }

    public MutableLiveData<List<ChatModel.ChatsData>> sendMessage(int id_sender, int id_receiver, String message) {
        MutableLiveData<List<ChatModel.ChatsData>> liveDataKos = new MutableLiveData<>();
        RequestBody messageVal = RequestBody.create(MediaType.parse("text/plain"), message);
        apiService.sendMessage(id_sender, id_receiver,message).enqueue(new Callback<ChatModel>() {
            @Override
            public void onResponse(Call<ChatModel> call, Response<ChatModel> response) {

                Log.d("HAIDSAD", response.body().getStatus().toString());
                if (response.isSuccessful() && response.body() != null) {
                    liveDataKos.postValue(response.body().getData()); // Set data ke LiveData
                } else {
                    liveDataKos.postValue(new ArrayList<>()); // Data kosong jika response gagal
                }
            }

            @Override
            public void onFailure(Call<ChatModel> call, Throwable t) {
                Log.e("API_FAILURE", t.getMessage());
                liveDataKos.postValue(new ArrayList<>()); // Data kosong jika API gagal
            }
        });

        return liveDataKos;
    }

}