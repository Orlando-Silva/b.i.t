package com.app.bit;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.util.Log;

import com.app.bit.Workers.PendingDepositWorker;
import com.app.bit.Workers.PendingWithdrawWorker;
import com.app.bit.Workers.VerifyDepositWorker;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

import androidx.annotation.RequiresApi;
import androidx.work.WorkManager;

public class CustomApplicationInitiator extends Application  {

    private WorkManager mWorkManager;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onCreate() {
        super.onCreate();
        initiateServices();
        setSslProtocol();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initiateServices() {

        StartVerifyDeposit();
        StartPendingDeposit();
        StartPendingWithdraw();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void StartVerifyDeposit() {
        ComponentName componentName = new ComponentName(this, VerifyDepositWorker.class);
        JobInfo jobInfo = new JobInfo.Builder(12, componentName)
                .setBackoffCriteria(5000, JobInfo.BACKOFF_POLICY_LINEAR)
                .setMinimumLatency(30000)
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("Test", "Verify Deposit Job scheduled!");
        } else {
            Log.d("Test", "Verify Deposit Job not scheduled");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void StartPendingDeposit() {
        ComponentName componentName = new ComponentName(this, PendingDepositWorker.class);
        JobInfo jobInfo = new JobInfo.Builder(13, componentName)
                .setBackoffCriteria(5000, JobInfo.BACKOFF_POLICY_LINEAR)
                .setMinimumLatency(30000)
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("Test", "Pending Deposit Job scheduled!");
        } else {
            Log.d("Test", "Pending Deposit Job not scheduled");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void StartPendingWithdraw() {
        ComponentName componentName = new ComponentName(this, PendingWithdrawWorker.class);
        JobInfo jobInfo = new JobInfo.Builder(14, componentName)
                .setBackoffCriteria(5000, JobInfo.BACKOFF_POLICY_LINEAR)
                .setMinimumLatency(30000)
                .build();

        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(jobInfo);

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("Test", "Pending Withdraw Job scheduled!");
        } else {
            Log.d("Test", "Pending Withdraw Job not scheduled");
        }
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