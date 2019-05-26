package com.example.bit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bit.R;
import com.example.bit.databinding.ActivityMainBinding;

public class RegisterUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding =  DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(v);
            }
        });
    }

    public void registerUser(View view) {
        Intent i = new Intent(getBaseContext(), RegisterUserActivity.class);
        startActivity(i);
    }
}
