package com.app.bit.DAL.Repositories;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.app.bit.DAL.BitRoomDatabase;
import com.app.bit.DAL.DAO.DepositDao;
import com.app.bit.DAL.Entities.Address;
import com.app.bit.DAL.Entities.Deposit;
import com.app.bit.DAL.HttpResponseObjects.BlockcypherTransactionResponse;
import com.app.bit.DAL.HttpResponseObjects.Deposit.TransactionsByAddress;
import com.app.bit.DAL.HttpResponseObjects.Deposit.Txref;
import com.app.bit.DAL.HttpResponseObjects.Output;
import com.app.bit.Helpers.HttpHelpers;
import com.app.bit.R;

import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LiveData;

import static androidx.core.content.ContextCompat.getSystemService;

public class DepositRepository {

    private DepositDao mDepositDao;

    public DepositRepository(Application application) {
        BitRoomDatabase db = BitRoomDatabase.getInstance(application);
        mDepositDao = db.depositDao();
    }

    public void insert(Deposit deposit) { mDepositDao.insert(deposit); }

    public void delete(int id) { mDepositDao.delete(id); }

    public void deleteAll() {mDepositDao.deleteAll(); }

    public Deposit get(int id) { return mDepositDao.get(id); }

    public List<Deposit> getAll() { return mDepositDao.getAll(); }

    public List<Deposit> getAllByUser(int userId) { return mDepositDao.getAllByUser(userId); }

    public LiveData<List<Deposit>> getAllLiveDataByUser(int userId) { return mDepositDao.getAllLiveDataByUser(userId); }

    public List<Deposit> getByTxId(String txId) { return mDepositDao.getByTxId(txId); }

    public List<Deposit> getByAddress(int addressId) { return mDepositDao.getByAddress(addressId); }

    public List<Deposit> getByTxIdAndAddress(String txId, String address) { return mDepositDao.getByTxIdAndAddress(txId, address); }

    public List<Deposit> getUnconfirmedDeposits() { return mDepositDao.getUnconfirmedDeposits();}

    public void update(Deposit deposit) { mDepositDao.update(deposit); }

    public BlockcypherTransactionResponse getTransaction(String txId) {

        return HttpHelpers.makeGetRequest("https://api.blockcypher.com/v1/bcy/test/txs/" + txId + "?limit=5000&includeHex=false",
                new BlockcypherTransactionResponse().getClass());
    }

    public void verifyPendingDeposit(Deposit deposit) {

        BlockcypherTransactionResponse response = getTransaction(deposit.getTxId());

        if(response == null)
            return;


        if(response.getConfirmations() > deposit.getConfirmations()) {
            deposit.setConfirmations(response.getConfirmations());
            mDepositDao.update(deposit);
        }

    }

    public void verifyDepositsInBlockchain(Address address, Context context) {

        TransactionsByAddress response =  HttpHelpers.makeGetRequest("https://api.blockcypher.com/v1/bcy/test/addrs/" +
                address.getPublicAddress(), new TransactionsByAddress().getClass());

        if(response != null && response.getTxrefs() != null) {
            verifyTransactions(response.getTxrefs(), address, context);
        }
    }

    private void verifyTransactions(List<Txref> transactions, Address address, Context context) {

        for (Txref transaction: transactions) {

            List<Deposit> existingDeposits = mDepositDao.getByTxIdAndAddress(transaction.getTxHash(), address.getPublicAddress());

            if(existingDeposits == null  || existingDeposits.isEmpty()) {

                BlockcypherTransactionResponse transactionResponse = getTransaction(transaction.getTxHash());

                if(transactionResponse != null) {

                    double transactionTotal = verifyOutputs(transactionResponse.getOutputs(), address);

                    if (transactionTotal > 0) {

                        if (transactionResponse != null) {

                            Deposit deposit = new Deposit();
                            deposit.setAmount(transactionTotal);
                            deposit.setAddressId(address.getId());
                            deposit.setConfirmations(transactionResponse.getConfirmations());
                            deposit.setCreatedAt(new Date());
                            deposit.setTxId(transaction.getTxHash());
                            deposit.setUserId(address.getUserId());
                            mDepositDao.insert(deposit);
                            showNotification(context, new Random().nextInt(300));
                        }
                    }
                }
            }
        }
    }

    private void showNotification(Context context, int id) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "BITChannel")
                .setContentTitle("Notificação de depósito")
                .setContentText("Você recebeu um novo depósito!")
                .setSmallIcon(R.drawable.bitcoinlogo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(id, builder.build());
    }

    private void createNotificationChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BITChannel";
            String description = "Channel for sending deposit information";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("BITChannel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) getSystemService(context, NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private float verifyOutputs(List<Output> outputs, Address address) {

        float transactionTotal = 0;

        for (Output output : outputs) {

            if (output.getAddresses().get(0).equals(address.getPublicAddress())) {
                transactionTotal += Float.parseFloat(output.getValue() + "") / Float.parseFloat(100000000 + "");
            }
        }

        return transactionTotal;

    }

}
