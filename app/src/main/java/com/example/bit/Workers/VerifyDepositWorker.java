package com.example.bit.Workers;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Repositories.AddressRepository;
import com.example.bit.DAL.Repositories.DepositRepository;
import com.example.bit.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;

public class VerifyDepositWorker extends Worker {

    private DepositRepository mDepositRepository;
    private AddressRepository mAddressRepository;
    private Context mContext;

    @NonNull
    @Override
    public Result doWork() {
        try {
            Context applicationContext = getApplicationContext();
            mContext = applicationContext;
            mDepositRepository = new DepositRepository((Application)mContext.getApplicationContext());
            mAddressRepository = new AddressRepository((Application)mContext.getApplicationContext());

            verifyDeposits();
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
