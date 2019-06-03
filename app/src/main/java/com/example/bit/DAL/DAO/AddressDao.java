package com.example.bit.DAL.DAO;

import com.example.bit.DAL.Entities.Address;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface AddressDao {

    @Insert
    void insert(Address address);

    @Query("DELETE FROM Addresses WHERE ID = :id")
    void delete(int id);

    @Query("DELETE FROM Addresses")
    void deleteAll();

    @Query("SELECT * FROM Addresses WHERE Id = :id LIMIT 1")
    Address get(int id);

    @Query("SELECT * FROM Addresses WHERE UserId = :userId ORDER BY ID DESC")
    List<Address> getAllByUser(int userId);

    @Query("UPDATE Addresses SET Label = :label WHERE Id = :addressId")
    void addLabelToAddress(int addressId, String label);

}
