package com.example.bit.DAL.Repositories;

import android.app.Application;

import com.example.bit.DAL.BitRoomDatabase;
import com.example.bit.DAL.DAO.UserDao;
import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Enums.Status;
import com.example.bit.Helpers.HashEnum;
import com.example.bit.Helpers.HashHelpers;

import java.util.Date;

public class UserRepository {

    private UserDao mUserDao;

    public UserRepository(Application application) {
        BitRoomDatabase db = BitRoomDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    public void insert(User user) {
        mUserDao.insert(user);
    }

    public User get(int id) {
        return mUserDao.get(id);
    }

    public User generatePassword(User user, String password) {
        byte[] finalPassword = HashHelpers.Generate(password, HashEnum.MD5.name());
        user.setPassword(finalPassword);
        return user;
    }

    public User activateUser(User user) {
        user.setStatus(Status.ACTIVE);
        return user;
    }


    public User insertUser(User user, String password) {
        user = generatePassword(user, password);
        user = activateUser(user);
        insert(user);
        return user;
    }


    public User login(String login, String password) {
        byte[] finalPassword = HashHelpers.Generate(password, HashEnum.MD5.name());
        User user = mUserDao.getByLoginAndPassword(login, finalPassword);
        return user;
    }
}
