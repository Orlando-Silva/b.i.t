package com.example.bit.Workers;

import android.app.Application;
import android.content.Context;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.DAL.Repositories.DepositRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class VerifyDepositWorker extends Worker {

    private DepositRepository mDepositRepository;
    private AddressRepository mAddressRepository;


    public VerifyDepositWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mDepositRepository = new DepositRepository((Application)context.getApplicationContext());
        mAddressRepository = new AddressRepository((Application)context.getApplicationContext());
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            verifyDeposits();
            return Result.success();
        }
        catch (Exception exception) {
            return Result.failure();
        }
    }

    public void verifyDeposits() {

        List<Address> addresses = mAddressRepository.getAll();

        if(addresses == null || addresses.isEmpty())
            return;

        for (Address address: addresses) {
            mDepositRepository.verifyDepositsInBlockchain(address);
        }
    }
}
