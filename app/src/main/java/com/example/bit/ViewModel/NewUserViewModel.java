package com.example.bit.ViewModel;

import com.example.bit.DAL.Entities.User;

import androidx.lifecycle.ViewModel;

public class NewUserViewModel extends ViewModel {

    private User user;

    public NewUserViewModel(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
