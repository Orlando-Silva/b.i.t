package com.app.bit.View.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.bit.DAL.Entities.User;
import com.app.bit.DAL.Repositories.UserRepository;
import com.app.bit.Helpers.StringHelpers;
import com.app.bit.R;
import com.app.bit.View.IntentExtras.Constants;
import com.app.bit.Workers.PendingDepositWorker;
import com.app.bit.Workers.PendingWithdrawWorker;
import com.app.bit.Workers.VerifyDepositWorker;
import com.app.bit.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

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
                i.putExtra(Constants.USER_INTENT, user);
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
