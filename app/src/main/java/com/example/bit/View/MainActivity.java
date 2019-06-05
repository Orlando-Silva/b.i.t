package com.example.bit.View;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.UserRepository;
import com.example.bit.Helpers.StringHelpers;
import com.example.bit.R;
import com.example.bit.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.registerUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(v);
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

    }

    public void login(View v) {
        if(argsIsValid()) {
            UserRepository userRepository = new UserRepository(getApplication());
            User user = userRepository.login(binding.tvLogin.getEditText().getText().toString(), binding.tvPassword.getEditText().getText().toString());

            if(user != null) {
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                i.putExtra("User", user);
                startActivity(i);
                finish();
            }
            else {
                showMaterialMessage("Aviso", "Login ou senha errado!");
            }
        }
    }

    private boolean argsIsValid() {

        if(binding.tvLogin.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(binding.tvLogin.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Insira o login!");
            return false;
        }

        if(binding.tvPassword.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(binding.tvPassword.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Insira a senha!");
            return false;
        }

        return true;
    }

    private void showMaterialMessage(String title, String message) {
        new MaterialAlertDialogBuilder(this, R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Accept", null)
                .create()
                .show();
    }


    public void registerUser(View view) {
        Intent i = new Intent(MainActivity.this, RegisterUserActivity.class);
        startActivity(i);
    }


}
