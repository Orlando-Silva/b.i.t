package com.app.bit.Workers;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.app.bit.DAL.Entities.Withdraw;
import com.app.bit.DAL.Repositories.WithdrawRepository;

import java.util.List;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class PendingWithdrawWorker extends JobService {

    private boolean isWorking = false;
    private boolean jobCancelled = false;
    private WithdrawRepository mWithdrawRepository;
    private Context context;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("Test", "Pending Withdraw Job started!");
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
            Context applicationContext = getApplicationContext();
            this.context = applicationContext;
            mWithdrawRepository = new WithdrawRepository((Application) context.getApplicationContext());
            pendingWithdraws();
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

    public void pendingWithdraws() {

        List<Withdraw> unconfirmedWithdraws = mWithdrawRepository.getUnconfirmedWithdraws();

        if (unconfirmedWithdraws == null || unconfirmedWithdraws.isEmpty())
            return;

        for (Withdraw withdraw: unconfirmedWithdraws) {
            mWithdrawRepository.verifyPendingWithdraws(withdraw);
        }
    }
}
