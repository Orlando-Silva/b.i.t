package com.example.bit.DAL.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Deposits",
        foreignKeys = {
        @ForeignKey(entity = User.class,
                parentColumns = "Id",
                childColumns = "UserId",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,
                parentColumns = "Id",
                childColumns = "AddressId",
                onDelete = ForeignKey.CASCADE) })
public class Deposit {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    private int id;

    @NonNull
    @ColumnInfo(name = "Amount")
    private double amount;

    @NonNull
    @ColumnInfo(name = "UserId")
    private int userId;

    @NonNull
    @ColumnInfo(name = "AddressId")
    private int addressId;

    @NonNull
    @ColumnInfo(name = "Confirmations")
    private int confirmations;

    @NonNull
    @ColumnInfo(name = "TxId")
    private String txId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(int confirmations) {
        this.confirmations = confirmations;
    }

    @NonNull
    public String getTxId() {
        return txId;
    }

    public void setTxId(@NonNull String txId) {
        this.txId = txId;
    }
}
