package com.app.bit.Workers;

import android.app.Application;
import android.content.Context;

import com.app.bit.DAL.Entities.Deposit;
import com.app.bit.DAL.Repositories.DepositRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.work.Worker;

public class PendingDepositWorker extends Worker {

    private DepositRepository mDepositRepository;
    private Context context;

    @NonNull
    @Override
    public Result doWork() {
        try {
            /*
            Context applicationContext = getApplicationContext();
            this.context = applicationContext;
            mDepositRepository = new DepositRepository((Application) context.getApplicationContext());
            pendingDeposits();
            */
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
