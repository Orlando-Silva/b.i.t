package com.example.bit.View.ViewModels;

import android.app.Application;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Repositories.AddressRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


public class AddressViewModel extends AndroidViewModel {

    private AddressRepository mAddressRepository;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        mAddressRepository = new AddressRepository(application);
    }

    public LiveData<List<Address>> getAllLiveDataByUser(int userId) { return mAddressRepository.getAllLiveDataByUser(userId); }
}
