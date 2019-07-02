package com.app.bit.Workers;

import android.app.Application;
import android.content.Context;

import com.app.bit.DAL.Entities.Withdraw;
import com.app.bit.DAL.Repositories.WithdrawRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;

public class PendingWithdrawWorker extends Worker {

    private WithdrawRepository mWithdrawRepository;
    private Context context;

    @NonNull
    @Override
    public Result doWork() {
        try {
            /*
            Context applicationContext = getApplicationContext();
            this.context = applicationContext;
            mWithdrawRepository = new WithdrawRepository((Application) context.getApplicationContext());
            pendingWithdraws();
            */
            return Result.SUCCESS;
        } catch (Exception exception) {
            return Result.FAILURE;
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
