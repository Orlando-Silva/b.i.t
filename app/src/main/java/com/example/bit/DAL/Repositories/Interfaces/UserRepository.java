package com.example.bit.DAL.Repositories.Interfaces;

import com.example.bit.DAL.Entities.User;

public interface UserRepository {

    void insert(User user);

    User get(int id);
}
