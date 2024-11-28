package com.jedu.re_kos.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jedu.re_kos.model.response.DetailResponse;
import com.jedu.re_kos.repository.DetailRepository;

public class DetailViewModel extends ViewModel {
    private final DetailRepository detailRepository;

    public DetailViewModel() {
        detailRepository = new DetailRepository();
    }

    public LiveData<DetailResponse> getDetail(int id) {
        return detailRepository.getDetail(id);
    }
}
