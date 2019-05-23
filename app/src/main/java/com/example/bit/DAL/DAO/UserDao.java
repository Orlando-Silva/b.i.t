package com.example.bit.DAL.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.bit.DAL.Entities.User;

import java.util.List;

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
