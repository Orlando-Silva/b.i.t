package com.example.bit;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.bit.Workers.PendingDepositWorker;
import com.example.bit.Workers.VerifyDepositWorker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import androidx.work.Configuration;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkerFactory;

public class DefaultApplication extends Application {

    private WorkManager mWorkManager;

    @Override
    public void onCreate() {
        super.onCreate();
        setSslProtocol();
        initiateWorkers();

    }

    private void initiateWorkers() {

        mWorkManager = WorkManager.getInstance();

        OneTimeWorkRequest verifydepositWorkerFirst = new OneTimeWorkRequest.Builder(VerifyDepositWorker.class).build();
        OneTimeWorkRequest pendingdepositWorkerFirst= new OneTimeWorkRequest.Builder(PendingDepositWorker.class).build();

        PeriodicWorkRequest verifydepositWorker = new PeriodicWorkRequest.Builder(VerifyDepositWorker.class, 20, TimeUnit.MINUTES).build();
        PeriodicWorkRequest pendingdepositWorker = new PeriodicWorkRequest.Builder(PendingDepositWorker.class, 20, TimeUnit.MINUTES).build();

        mWorkManager.beginWith(verifydepositWorkerFirst).enqueue();
        mWorkManager.beginWith(pendingdepositWorkerFirst).enqueue();

        mWorkManager.enqueueUniquePeriodicWork("VerifyDepositWorker", ExistingPeriodicWorkPolicy.KEEP, verifydepositWorker);
        mWorkManager.enqueueUniquePeriodicWork("PendingDepositWorker", ExistingPeriodicWorkPolicy.KEEP, pendingdepositWorker);
    }

    private void setSslProtocol() {

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

    }


}