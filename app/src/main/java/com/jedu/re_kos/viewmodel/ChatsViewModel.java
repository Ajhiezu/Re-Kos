package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import com.jedu.re_kos.Model.ChatModel;
import com.jedu.re_kos.Model.response.UserResponse;
import com.jedu.re_kos.repository.ChatsRepository;
import com.jedu.re_kos.repository.UserRepository;

import java.util.List;

public class ChatsViewModel extends ViewModel {
    private final ChatsRepository repository;

    public ChatsViewModel() {
        repository = new ChatsRepository();
    }

    public LiveData<List<ChatModel.ChatsData>> getListChat(int id) {
        return repository.getListChat(id);
    }
    public LiveData<List<ChatModel.ChatsData>> getDetailChat(int id_sender,int id_receiver) {
        return repository.getDetailChat(id_sender,id_receiver);
    } public LiveData<List<ChatModel.ChatsData>> sendMessage(int id_sender,int id_receiver,String message) {
        return repository.sendMessage(id_sender,id_receiver,message);
    }


}
