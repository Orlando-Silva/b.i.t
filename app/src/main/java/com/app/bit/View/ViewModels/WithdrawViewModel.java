package com.app.bit.View.ViewModels;

import android.app.Application;

import com.app.bit.DAL.Entities.Withdraw;
import com.app.bit.DAL.Repositories.WithdrawRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class WithdrawViewModel extends AndroidViewModel {

    private WithdrawRepository mWithdrawRepository;

    public WithdrawViewModel(@NonNull Application application) {
        super(application);
        mWithdrawRepository = new WithdrawRepository(application);
    }

    public LiveData<List<Withdraw>> getAllLiveDataByUser(int userId) {
        return mWithdrawRepository.getAllLiveDataByUser(userId);
    }
}