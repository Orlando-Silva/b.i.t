package com.example.bit.DAL.Repositories;

import android.app.Application;

import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.UserDao;
import com.example.bit.DAL.Entities.User;

public class UserRepository implements com.example.bit.DAL.Repositories.Interfaces.UserRepository {

    private UserDao mUserDao;

    public UserRepository(Application application) {
        BitRoomDatabase db = BitRoomDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    @Override
    public void insert(User user) {
        mUserDao.insert(user);
    }

    @Override
    public User get(int id) {
        return mUserDao.get(id);
    }
}
