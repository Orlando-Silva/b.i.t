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

    @Query("SELECT SUM(balance) FROM ( SELECT UserId, Amount as balance FROM Deposits WHERE Confirmations > 6 AND UserId = :userId UNION ALL SELECT UserId, -Amount as balance FROM Withdraws WHERE UserId = :userId)")
    double getBalance(int userId);

}
