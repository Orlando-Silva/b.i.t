package com.example.bit.DAL.DAO;

import com.example.bit.DAL.Entities.Deposit;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DepositDao {

    @Insert
    void insert(Deposit deposit);

    @Query("DELETE FROM Deposits WHERE ID = :id")
    void delete(int id);

    @Query("DELETE FROM Deposits")
    void deleteAll();

    @Query("SELECT * FROM Deposits WHERE Id = :id LIMIT 1")
    Deposit get(int id);

    @Query("SELECT * FROM Deposits")
    List<Deposit> getAll();

    @Query("SELECT * FROM Deposits WHERE UserId = :userId ORDER BY ID DESC")
    List<Deposit> getAllByUser(int userId);

    @Query("SELECT * FROM Deposits WHERE UserId = :userId ORDER BY ID DESC")
    LiveData<List<Deposit>> getAllLiveDataByUser(int userId);

    @Query("SELECT * FROM Deposits WHERE TxId = :txId")
    List<Deposit> getByTxId(String txId);

    @Query("SELECT * FROM Deposits WHERE AddressId = :addressId")
    List<Deposit> getByAddress(int addressId);

    @Query("SELECT d.* FROM Deposits d INNER JOIN Addresses a ON a.Id = d.AddressId  WHERE a.publicAddress = :address AND d.TxId = :txId")
    List<Deposit> getByTxIdAndAddress(String txId, String address);

    @Query("SELECT * FROM Deposits WHERE Confirmations < 7")
    List<Deposit> getUnconfirmedDeposits();

    @Update
    void update(Deposit deposit);

}
