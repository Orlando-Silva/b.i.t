package com.app.bit.Workers;

import android.app.Application;
import android.content.Context;

import com.app.bit.DAL.Entities.Address;
import com.app.bit.DAL.Repositories.AddressRepository;
import com.app.bit.DAL.Repositories.DepositRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;

public class VerifyDepositWorker extends Worker {

    private DepositRepository mDepositRepository;
    private AddressRepository mAddressRepository;
    private Context mContext;

    @NonNull
    @Override
    public Result doWork() {
        try {
            /*
            Context applicationContext = getApplicationContext();
            mContext = applicationContext;
            mDepositRepository = new DepositRepository((Application)mContext.getApplicationContext());
            mAddressRepository = new AddressRepository((Application)mContext.getApplicationContext());

            verifyDeposits();
            */
            return Result.SUCCESS;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return Result.FAILURE;
        }
    }

    public void verifyDeposits() {

        List<Address> addresses = mAddressRepository.getAll();

        if(addresses == null || addresses.isEmpty())
            return;

        for (Address address: addresses) {
            mDepositRepository.verifyDepositsInBlockchain(address, mContext);
        }
    }


}
