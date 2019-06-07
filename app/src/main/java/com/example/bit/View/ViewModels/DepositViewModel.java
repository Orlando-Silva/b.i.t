package com.example.bit.View.ViewModels;

import android.app.Application;

import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Repositories.DepositRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DepositViewModel extends AndroidViewModel {

    private DepositRepository mDepositRepository;

    public DepositViewModel(@NonNull Application application) {
        super(application);
        mDepositRepository = new DepositRepository(application);
    }

    public LiveData<List<Deposit>> getAllLiveDataByUser(int userId) {
        return mDepositRepository.getAllLiveDataByUser(userId);
    }
}
