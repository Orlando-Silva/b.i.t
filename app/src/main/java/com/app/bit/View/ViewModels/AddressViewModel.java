package com.app.bit.View.ViewModels;

import android.app.Application;

import com.app.bit.DAL.Entities.Address;
import com.app.bit.DAL.Repositories.AddressRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class AddressViewModel extends AndroidViewModel {

    private AddressRepository mAddressRepository;

    private MutableLiveData<List<Address>> mAllAddresses;

    public AddressViewModel(@NonNull Application application) {
        super(application);
        mAddressRepository = new AddressRepository(application);
    }

    public LiveData<List<Address>> getAllLiveDataByUser(int userId) {
        return mAddressRepository.getAllLiveDataByUser(userId);
    }

    public List<Address> getAllByUser(int userId) {
        return mAddressRepository.getAllByUser(userId);
    }

    public Address generateAddress(int userId) throws Exception {
        return mAddressRepository.generateAddress(userId);
    }

    public Address generateFirstAddress(int userId) throws Exception {
        return mAddressRepository.generateFirstAddress(userId);
    }
}
