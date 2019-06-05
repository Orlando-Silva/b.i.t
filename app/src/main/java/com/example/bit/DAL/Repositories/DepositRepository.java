package com.example.bit.DAL.Repositories;

import android.app.Application;

import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.DepositDao;
import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.HttpResponseObjects.BlockchainGetLatestBlockResponse;
import com.example.bit.DAL.HttpResponseObjects.BlockchainRawAddressResponse;
import com.example.bit.DAL.HttpResponseObjects.BlockchainRawTransactionResponse;
import com.example.bit.DAL.HttpResponseObjects.BlockcypherCreateAddressResponse;
import com.example.bit.DAL.HttpResponseObjects.Out;
import com.example.bit.DAL.HttpResponseObjects.Tx;
import com.example.bit.Helpers.HttpHelpers;

import java.util.List;

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

    public List<Deposit> getAllByUser(int userId) { return mDepositDao.getAllByUser(userId); }

    public List<Deposit> getByTxId(String txId) { return mDepositDao.getByTxId(txId); }

    public List<Deposit> getByAddress(int addressId) { return mDepositDao.getByAddress(addressId); }

    public List<Deposit> getByTxIdAndAddress(String txId, String address) { return mDepositDao.getByTxIdAndAddress(txId, address); }

    public List<Deposit> getUnconfirmedDeposits() { return mDepositDao.getUnconfirmedDeposits();}

    public void update(Deposit deposit) { mDepositDao.update(deposit); }

    public void verifyPendingDeposit(Deposit deposit) {

        BlockchainRawTransactionResponse response =  HttpHelpers.makePostRequest("https://testnet.blockchain.info/rawtx/" + deposit.getTxId(),
                null, new BlockchainRawTransactionResponse().getClass());

        if(response == null)
            return;

        int transactionConfirmations = getTransactionConfirmations(response.getBlockHeight());

        if(transactionConfirmations > deposit.getConfirmations()) {
            deposit.setConfirmations(transactionConfirmations);
            mDepositDao.update(deposit);
        }

    }

    public void verifyDepositsInBlockchain(Address address) {

        BlockchainRawAddressResponse response =  HttpHelpers.makePostRequest("https://testnet.blockchain.info/rawaddr/" + address.getPublicAddress(),
                null, new BlockchainRawAddressResponse().getClass());

        verifyTransactions(response.getTxs(), address);

    }

    private void verifyTransactions(List<Tx> transactions, Address address) {

        for (Tx transaction: transactions) {

            List<Deposit> existingDeposits = mDepositDao.getByTxIdAndAddress(transaction.getHash(), address.getPublicAddress());

            if(existingDeposits == null  || existingDeposits.isEmpty()) {

                double transactionTotal = verifyOutputs(transaction.getOut(), address);

                if(transactionTotal > 0)  {

                    Deposit deposit = new Deposit();
                    deposit.setAmount(transactionTotal);
                    deposit.setAddressId(address.getId());
                    deposit.setConfirmations(getTransactionConfirmations(transaction.getBlockHeight()));
                    deposit.setTxId(transaction.getHash());
                    deposit.setUserId(address.getUserId());

                    mDepositDao.insert(deposit);
                }
            }
        }
    }

    private double verifyOutputs(List<Out> outputs, Address address) {

        double transactionTotal = 0;

        for (Out output: outputs) {

            if(output.getAddr() == address.getPublicAddress()) {
                transactionTotal += output.getValue() / 100000000;
            }
        }

        return transactionTotal;
    }

    private int getTransactionConfirmations(int transactionBlockHeigh) {

        BlockchainGetLatestBlockResponse response =  HttpHelpers.makePostRequest("https://testnet.blockchain.info/latestblock",
                null, new BlockchainGetLatestBlockResponse().getClass());

        if(response != null) {
            return response.getHeight() - transactionBlockHeigh + 1;
        }
        else {
            return 0;
        }
    }

}
