package com.example.bit.DAL.DAO;

import com.example.bit.DAL.Entities.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM Users WHERE ID = :id")
    void delete(int id);

    @Query("DELETE FROM Users")
    void deleteAll();

    @Query("SELECT * FROM Users WHERE Id = :id LIMIT 1")
    User get(int id);

    @Query("SELECT * FROM Users ORDER BY ID DESC")
    List<User> getAllUsers();
}
