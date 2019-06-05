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

import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Repositories.DepositRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PendingDepositWorker extends Worker {

    private DepositRepository mDepositRepository;

    public PendingDepositWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mDepositRepository = new DepositRepository((Application)context.getApplicationContext());
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        try {
            pendingDeposits();
            return ListenableWorker.Result.success();
        }
        catch (Exception exception) {
            return ListenableWorker.Result.failure();
        }
    }

    public void pendingDeposits() {

        List<Deposit> unconfirmedDeposits = mDepositRepository.getUnconfirmedDeposits();

        if(unconfirmedDeposits == null || unconfirmedDeposits.isEmpty())
            return;

        for (Deposit deposit: unconfirmedDeposits) {
            mDepositRepository.verifyPendingDeposit(deposit);
        }

    }
}
