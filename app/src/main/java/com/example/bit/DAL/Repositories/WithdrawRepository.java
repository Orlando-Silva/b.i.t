package com.example.bit.DAL.Repositories;

import android.app.Application;

import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.WithdrawDao;
import com.example.bit.DAL.Entities.Withdraw;

import java.util.List;

import androidx.lifecycle.LiveData;

public class WithdrawRepository {

    private WithdrawDao mWithdrawDao;

    public WithdrawRepository(Application application) {
        BitRoomDatabase db = BitRoomDatabase.getInstance(application);
        mWithdrawDao = db.withdrawDao();
    }

    public void insert(Withdraw withdraw) { mWithdrawDao.insert(withdraw); }

    public void delete(int id) { mWithdrawDao.delete(id); }

    public void deleteAll() { mWithdrawDao.deleteAll(); }

    public Withdraw get(int id) { return mWithdrawDao.get(id); }

    public List<Withdraw> getAll() { return mWithdrawDao.getAll(); }

    public List<Withdraw> getAllByUser(int userId) { return mWithdrawDao.getAllByUser(userId); }

    public LiveData<List<Withdraw>> getAllLiveDataByUser(int userId) { return mWithdrawDao.getAllLiveDataByUser(userId); }

    public List<Withdraw> getByTxId(String txId) { return mWithdrawDao.getByTxId(txId); }

    public List<Withdraw> getByAddress(int addressId) { return mWithdrawDao.getByAddress(addressId); }

    public List<Withdraw> getByTxIdAndAddress(String txId, String address) { return mWithdrawDao.getByTxIdAndAddress(txId, address); }

    public List<Withdraw> getUnconfirmedWithdraws() { return mWithdrawDao.getUnconfirmedWithdraws(); }

    public void update(Withdraw withdraw) { mWithdrawDao.update(withdraw); }
}
