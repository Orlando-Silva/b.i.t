package com.example.bit.Workers;

import android.app.Application;
import android.content.Context;

import com.example.bit.DAL.Entities.Withdraw;
import com.example.bit.DAL.Repositories.WithdrawRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PendingWithdrawWorker extends Worker {

    private WithdrawRepository mWithdrawRepository;
    private Context context;

    public PendingWithdrawWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
        mWithdrawRepository = new WithdrawRepository((Application) context.getApplicationContext());
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        try {
            pendingWithdraws();
            return ListenableWorker.Result.success();
        } catch (Exception exception) {
            return ListenableWorker.Result.failure();
        }
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
