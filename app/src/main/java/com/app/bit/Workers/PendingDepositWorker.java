package com.app.bit.Workers;

import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.app.bit.DAL.Entities.Deposit;
import com.app.bit.DAL.Repositories.DepositRepository;

import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PendingDepositWorker extends JobService {

    private DepositRepository mDepositRepository;
    private Context context;

    private boolean isWorking = false;
    private boolean jobCancelled = false;


    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.d("Test", "Pending Deposit Job started!");
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
            mDepositRepository = new DepositRepository((Application) context.getApplicationContext());
            pendingDeposits();
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

    public void pendingDeposits() {

        List<Deposit> unconfirmedDeposits = mDepositRepository.getUnconfirmedDeposits();

        if (unconfirmedDeposits == null || unconfirmedDeposits.isEmpty())
            return;

        for (Deposit deposit : unconfirmedDeposits) {
            mDepositRepository.verifyPendingDeposit(deposit);

        }
    }
}
