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
import androidx.work.WorkerParameters;

public class VerifyDepositWorker extends Worker {

    private DepositRepository mDepositRepository;
    private AddressRepository mAddressRepository;
    private Context mContext;

    public VerifyDepositWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mContext = context;
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
            exception.printStackTrace();
            return Result.failure();
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
