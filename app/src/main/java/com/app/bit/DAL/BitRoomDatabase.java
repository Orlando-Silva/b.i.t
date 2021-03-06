package com.app.bit.DAL;

import android.content.Context;

import com.app.bit.DAL.DAO.AddressDao;
import com.app.bit.DAL.DAO.DepositDao;
import com.app.bit.DAL.DAO.UserDao;
import com.app.bit.DAL.DAO.WithdrawDao;
import com.app.bit.DAL.Entities.Address;
import com.app.bit.DAL.Entities.Deposit;
import com.app.bit.DAL.Entities.User;
import com.app.bit.DAL.Entities.Withdraw;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Address.class, Deposit.class, Withdraw.class}, version = 13,exportSchema = false)
public abstract class BitRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract AddressDao addressDao();
    public abstract DepositDao depositDao();
    public abstract WithdrawDao withdrawDao();

    private static volatile BitRoomDatabase INSTANCE;

    public static BitRoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BitRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BitRoomDatabase.class, "BIT.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
