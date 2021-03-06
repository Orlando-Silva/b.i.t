package com.app.bit.DAL.Entities;

import com.app.bit.DAL.Enums.Status;
import com.app.bit.DAL.Helpers.EnumConverter;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "Addresses",
        foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "Id",
        childColumns = "UserId",
        onDelete = ForeignKey.CASCADE))
public class Address {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    private int id;

    @NonNull
    @ColumnInfo(name = "publicAddress")
    private String publicAddress;

    @NonNull
    @ColumnInfo(name = "PublicKey")
    private String publicKey;

    @NonNull
    @ColumnInfo(name = "PrivateKey")
    private String privateKey;

    @ColumnInfo(name = "Label")
    private String label;

    @NonNull
    @ColumnInfo(name = "Status")
    @TypeConverters({EnumConverter.class})
    private Status status;

    @ColumnInfo(name = "UserId")
    private int userId;

    @ColumnInfo(name = "Wif")
    private String wif;

    public Address() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getPublicAddress() {
        return publicAddress;
    }

    public void setPublicAddress(@NonNull String publicAddress) {
        this.publicAddress = publicAddress;
    }

    @NonNull
    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(@NonNull String publicKey) {
        this.publicKey = publicKey;
    }

    @NonNull
    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(@NonNull String privateKey) {
        this.privateKey = privateKey;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    public void setStatus(@NonNull Status status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getWif() {
        return wif;
    }

    public void setWif(String wif) {
        this.wif = wif;
    }
}
