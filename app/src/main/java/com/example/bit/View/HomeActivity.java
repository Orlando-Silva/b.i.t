package com.example.bit.View;

import android.os.Bundle;
import android.app.Activity;

import com.example.bit.DAL.Entities.User;
import com.example.bit.R;
import com.example.bit.databinding.ActivityHomeBinding;

import androidx.databinding.DataBindingUtil;

public class HomeActivity extends Activity {

    ActivityHomeBinding bindingContent;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_home);
        user = (User) getIntent().getSerializableExtra("User");
        bindingContent.response.setText("Olá" + user.getName());
    }

}
