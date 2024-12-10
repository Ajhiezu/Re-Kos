package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.Model.ImageKosResponse;
import com.jedu.re_kos.repository.ImageKosRepository;

public class ImageKosViewModel extends ViewModel {
    private ImageKosRepository imageKosRepository;

    public ImageKosViewModel(){
       imageKosRepository = new ImageKosRepository();
    }

    public LiveData<ImageKosResponse> getImageKos(String id){
        return imageKosRepository.fetchImage(id);
    }

}
