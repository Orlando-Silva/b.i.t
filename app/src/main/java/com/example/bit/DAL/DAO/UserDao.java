package com.example.bit.DAL.DAO;

import com.example.bit.DAL.Entities.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM Users WHERE ID = :id")
    void delete(int id);

    @Query("DELETE FROM Users")
    void deleteAll();

    @Query("SELECT * FROM Users WHERE Id = :id LIMIT 1")
    User get(int id);

    @Query("SELECT * FROM Users ORDER BY ID DESC")
    List<User> getAllUsers();

    @Query("SELECT * FROM Users WHERE Login = :login AND Password = :password LIMIT 1")
    User getByLoginAndPassword(String login, byte[] password);

    @Query("SELECT (credit - debit) as balance FROM (SELECT SUM(d.Amount) as credit, d.UserId FROM Deposits d WHERE d.Confirmations > 6 AND d.UserId = :userId GROUP BY d.UserId) as credit LEFT JOIN (SELECT SUM(w.Amount) as debit, w.UserId FROM Withdraws w WHERE w.UserId = :userId GROUP BY w.UserId) as debit  ON debit.UserId = credit.UserId LIMIT 1")
    double getBalance(int userId);


}
