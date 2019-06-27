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
import com.example.bit.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;

public class PendingDepositWorker extends Worker {

    private DepositRepository mDepositRepository;
    private Context context;

    @NonNull
    @Override
    public Result doWork() {
        try {
            Context applicationContext = getApplicationContext();
            this.context = applicationContext;
            mDepositRepository = new DepositRepository((Application) context.getApplicationContext());

            pendingDeposits();
            return Result.SUCCESS;
        } catch (Exception exception) {
            return Result.FAILURE;
        }
    }

    public void pendingDeposits() {

        List<Deposit> unconfirmedDeposits = mDepositRepository.getUnconfirmedDeposits();

        if (unconfirmedDeposits == null || unconfirmedDeposits.isEmpty())
            return;

        for (Deposit deposit : unconfirmedDeposits) {
            mDepositRepository.verifyPendingDeposit(deposit);

        }
    }
}
