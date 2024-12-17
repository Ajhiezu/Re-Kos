package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.Model.DataModel;
import com.jedu.re_kos.Model.LoginResponse;
import com.jedu.re_kos.Model.request.RegisRequest;
import com.jedu.re_kos.repository.DataRepository;

import okhttp3.ResponseBody;

public class DataViewModel extends ViewModel {
    private final DataRepository repository;
    private MutableLiveData<DataModel> dataModel = new MutableLiveData<>();

    public DataViewModel() {
        repository = new DataRepository();
    }

    public void setData(DataModel data) {
        dataModel.setValue(data);
    }


    public LiveData<LoginResponse> login(String email, String password) {
        return repository.login(email, password);
    }

    public LiveData<ResponseBody> register(RegisRequest regisRequest){
        return repository.register(regisRequest);
    }

}
