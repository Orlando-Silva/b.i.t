package com.example.bit.DAL.Repositories;

import android.app.Application;

import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.DepositDao;
import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Helpers.DateConverter;
import com.example.bit.DAL.HttpResponseObjects.BlockchainGetLatestBlockResponse;
import com.example.bit.DAL.HttpResponseObjects.BlockchainRawAddressResponse;
import com.example.bit.DAL.HttpResponseObjects.BlockchainRawTransactionResponse;
import com.example.bit.DAL.HttpResponseObjects.BlockcypherTransactionResponse;
import com.example.bit.DAL.HttpResponseObjects.Deposit.TransactionsByAddress;
import com.example.bit.DAL.HttpResponseObjects.Deposit.Txref;
import com.example.bit.DAL.HttpResponseObjects.Out;
import com.example.bit.DAL.HttpResponseObjects.Output;
import com.example.bit.DAL.HttpResponseObjects.Tx;
import com.example.bit.Helpers.HttpHelpers;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

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

        return HttpHelpers.makeGetRequest("https://api.blockcypher.com/v1/btc/test3/txs/" + txId + "?limit=5000&includeHex=false",
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

    public void verifyDepositsInBlockchain(Address address) {

        TransactionsByAddress response =  HttpHelpers.makeGetRequest("https://api.blockcypher.com/v1/btc/test3/addrs/" +
                address.getPublicAddress(), new TransactionsByAddress().getClass());

        verifyTransactions(response.getTxrefs(), address);

    }

    private void verifyTransactions(List<Txref> transactions, Address address) {

        for (Txref transaction: transactions) {

            List<Deposit> existingDeposits = mDepositDao.getByTxIdAndAddress(transaction.getTxHash(), address.getPublicAddress());

            if(existingDeposits == null  || existingDeposits.isEmpty()) {

                BlockcypherTransactionResponse transactionResponse = getTransaction(transaction.getTxHash());

                double transactionTotal = verifyOutputs(transactionResponse.getOutputs(), address);

                if(transactionTotal > 0)  {

                    if(transactionResponse != null) {

                        Deposit deposit = new Deposit();
                        deposit.setAmount(transactionTotal);
                        deposit.setAddressId(address.getId());
                        deposit.setConfirmations(transactionResponse.getConfirmations());
                        deposit.setCreatedAt(new Date());
                        deposit.setTxId(transaction.getTxHash());
                        deposit.setUserId(address.getUserId());

                        mDepositDao.insert(deposit);
                    }


                }
            }
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
