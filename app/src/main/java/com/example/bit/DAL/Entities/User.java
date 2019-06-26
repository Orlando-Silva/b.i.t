package com.example.bit.DAL.Entities;


import com.example.bit.DAL.Enums.Status;
import com.example.bit.DAL.Helpers.EnumConverter;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

@Entity(tableName = "Users")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    private int id;

    @NonNull
    @ColumnInfo(name = "Name")
    private String name;


    @NonNull
    @ColumnInfo(name = "Email")
    private String email;

    @NonNull
    @ColumnInfo(name = "Login")
    private String login;

    @NonNull
    @ColumnInfo(name = "Password")
    private byte[] password;

    @NonNull
    @ColumnInfo(name = "Status")
    @TypeConverters({EnumConverter.class})
    private Status status;

    @NonNull
    @ColumnInfo(name = "ReceivesEmailOnWithdraw")
    private boolean receivesEmailOnWithdraw;

    public User() {

    }

    @Ignore
    public User(int id, @NonNull String name, @NonNull String email, @NonNull String login, @NonNull byte[] password, @NonNull Status status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isReceivesEmailOnWithdraw() {
        return receivesEmailOnWithdraw;
    }

    public void setReceivesEmailOnWithdraw(boolean receivesEmailOnWithdraw) {
        this.receivesEmailOnWithdraw = receivesEmailOnWithdraw;
    }
}
