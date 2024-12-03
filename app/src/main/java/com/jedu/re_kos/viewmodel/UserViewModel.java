package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.Model.request.UpdateRequest;
import com.jedu.re_kos.Model.response.UpdateRespon;
import com.jedu.re_kos.Model.response.UserResponse;
import com.jedu.re_kos.repository.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;

    public UserViewModel() {
        userRepository = new UserRepository();
    }

    public LiveData<UserResponse> getData(int id){
        return userRepository.getData(id);
    }

    public LiveData<UpdateRespon> updateUser(UpdateRequest updateRequest) {
        return userRepository.updateUser(updateRequest);
    }

}
