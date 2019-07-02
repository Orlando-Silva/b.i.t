package com.app.bit.DAL.DAO;

import com.app.bit.DAL.Entities.Withdraw;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WithdrawDao {

    @Insert
    void insert(Withdraw withdraw);

    @Query("DELETE FROM Withdraws WHERE ID = :id")
    void delete(int id);

    @Query("DELETE FROM Withdraws")
    void deleteAll();

    @Query("SELECT * FROM Withdraws WHERE Id = :id LIMIT 1")
    Withdraw get(int id);

    @Query("SELECT * FROM Withdraws ORDER BY ID DESC")
    List<Withdraw> getAll();

    @Query("SELECT * FROM Withdraws WHERE UserId = :userId ORDER BY ID DESC")
    List<Withdraw> getAllByUser(int userId);

    @Query("SELECT * FROM Withdraws WHERE UserId = :userId ORDER BY ID DESC")
    LiveData<List<Withdraw>> getAllLiveDataByUser(int userId);

    @Query("SELECT * FROM Withdraws WHERE TxId = :txId")
    List<Withdraw> getByTxId(String txId);

    @Query("SELECT * FROM Withdraws WHERE Confirmations < 7")
    List<Withdraw> getUnconfirmedWithdraws();

    @Update
    void update(Withdraw withdraw);
}
