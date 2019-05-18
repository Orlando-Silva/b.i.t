package com.example.bit.Core.Entities;

import com.example.bit.DAL.Helpers.Annotations.SqliteNotNull;
import com.example.bit.DAL.Helpers.Annotations.SqlitePrimaryKey;
import com.example.bit.DAL.Helpers.Annotations.SqliteTableName;
import com.example.bit.DAL.Helpers.Interfaces.SqliteGenericObject;

import java.io.Serializable;
import java.util.Date;

@SqliteTableName("Users")
public class User  implements SqliteGenericObject, Serializable {

    @SqlitePrimaryKey
    private int id;

    @SqliteNotNull
    private String name;

    @SqliteNotNull
    private String email;

    @SqliteNotNull
    private String login;

    @SqliteNotNull
    private String password;

    @SqliteNotNull
    private Date createdAt;

    @SqliteNotNull
    private Status status;

    public User() {
        
    }

    public User(int id, String name, String email, String login, String password, Date createdAt, Status status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.createdAt = createdAt;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
