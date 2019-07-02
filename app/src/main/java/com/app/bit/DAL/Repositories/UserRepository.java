package com.app.bit.DAL.Repositories;

import android.app.Application;

import com.app.bit.DAL.BitRoomDatabase;
import com.app.bit.DAL.DAO.UserDao;
import com.app.bit.DAL.Entities.User;
import com.app.bit.DAL.Enums.Status;
import com.app.bit.Helpers.HashEnum;
import com.app.bit.Helpers.HashHelpers;

public class UserRepository {

    private UserDao mUserDao;

    public UserRepository(Application application) {
        BitRoomDatabase db = BitRoomDatabase.getInstance(application);
        mUserDao = db.userDao();
    }

    public void insert(User user) {
        mUserDao.insert(user);
    }

    public void update(User user) { mUserDao.update(user); }

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

    public double getBalance(int userId) { return mUserDao.getBalance(userId); }

}
