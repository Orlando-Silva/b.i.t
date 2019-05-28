package com.example.bit.DAL.Repositories.Interfaces;

import com.example.bit.DAL.Entities.User;

public interface UserRepository {

    void insert(User user);

    User get(int id);

    User insertUser(User user, String password);

    User generatePassword(User user, String password);

    User activateUser(User user);

    User login(String login, String password);

}
