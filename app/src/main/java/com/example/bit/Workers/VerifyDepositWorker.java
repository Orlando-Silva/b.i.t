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
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class VerifyDepositWorker extends Worker {

    private DepositRepository mDepositRepository;
    private AddressRepository mAddressRepository;

    private Context context;

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
            exception.printStackTrace();
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

    private void showNotification(String Message, String name, String Information)
    {
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "Deposit_Notification_Chanel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Deposit Worker", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Deposit Worker Started");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setSound(null,null );
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), NOTIFICATION_CHANNEL_ID);

        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setAutoCancel(false)
                .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS)
                .setWhen(System.currentTimeMillis())
                .setSound(uri)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentTitle(Message)
                .setContentText(name)
                .setContentInfo(Information);

        notificationManager.notify(1, notificationBuilder.build());
    }
}
