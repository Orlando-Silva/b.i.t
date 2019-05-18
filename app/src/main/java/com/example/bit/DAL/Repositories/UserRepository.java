package com.example.bit.DAL.Repositories;

import android.content.Context;
import com.example.bit.Core.Entities.User;

public class UserRepository extends Repository<User> {

    public UserRepository(Context context) {
        super(context);
    }

    public User getById(int id) {
        User wrapper = new User();
        wrapper.setId(id);

        return queryById(wrapper);
    }

}
