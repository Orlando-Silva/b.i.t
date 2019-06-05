package com.example.bit.Workers;

import android.app.Application;
import android.content.Context;

import com.example.bit.DAL.Repositories.DepositRepository;

import androidx.annotation.NonNull;
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

    }
}
