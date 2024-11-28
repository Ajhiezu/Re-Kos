package com.jedu.re_kos.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jedu.re_kos.repository.KosRepository;
import com.jedu.re_kos.viewmodel.KosViewModel;

public class ViewModelFactory  implements  ViewModelProvider.Factory{
    private final KosRepository repository;

    public ViewModelFactory(KosRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(KosViewModel.class)) {
//            return (T) new KosViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
