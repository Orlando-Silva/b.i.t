package com.app.bit.Workers;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.app.bit.DAL.Entities.Address;
import com.app.bit.DAL.Repositories.AddressRepository;
import com.app.bit.DAL.Repositories.DepositRepository;

import java.util.List;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class VerifyDepositWorker extends JobService {

    private boolean isWorking = false;
    private boolean jobCancelled = false;
    private DepositRepository mDepositRepository;
    private AddressRepository mAddressRepository;
    private Context mContext;

    public void verifyDeposits() {

        try {


            mContext = getApplicationContext();
            mDepositRepository = new DepositRepository((Application) mContext.getApplicationContext());
            mAddressRepository = new AddressRepository((Application) mContext.getApplicationContext());

            List<Address> addresses = mAddressRepository.getAll();

            if (addresses == null || addresses.isEmpty())
                return;

            for (Address address : addresses) {
                mDepositRepository.verifyDepositsInBlockchain(address, mContext);
            }
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("Test", "Verify Deposit Job started!");
        isWorking = true;
        startWorkOnNewThread(jobParameters);
        return isWorking;
    }

    private void startWorkOnNewThread(final JobParameters jobParameters) {
        new Thread(new Runnable() {
            public void run() {
                doWork(jobParameters);
            }
        }).start();
    }

    private void doWork(JobParameters jobParameters) {

        if (jobCancelled)
            return;

        try {
            verifyDeposits();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        Log.d("Test", "Job finished!");
        isWorking = false;
        boolean needsReschedule = false;
        jobFinished(jobParameters, needsReschedule);
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d("Test", "Job cancelled before being completed.");
        jobCancelled = true;
        boolean needsReschedule = isWorking;
        jobFinished(jobParameters, needsReschedule);
        return needsReschedule;
    }
}
