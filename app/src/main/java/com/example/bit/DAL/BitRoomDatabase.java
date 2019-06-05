package com.example.bit.DAL;

import android.content.Context;

import com.example.bit.DAL.DAO.AddressDao;
import com.example.bit.DAL.DAO.DepositDao;
import com.example.bit.DAL.DAO.UserDao;
import com.example.bit.DAL.Entities.Address;
import com.example.bit.DAL.Entities.Deposit;
import com.example.bit.DAL.Entities.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Address.class, Deposit.class}, version = 5,exportSchema = false)
public abstract class BitRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract AddressDao addressDao();
    public abstract DepositDao depositDao();


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
