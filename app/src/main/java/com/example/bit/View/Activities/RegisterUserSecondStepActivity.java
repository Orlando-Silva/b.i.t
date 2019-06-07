package com.example.bit.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bit.DAL.Entities.User;
import com.example.bit.DAL.Repositories.UserRepository;
import com.example.bit.Helpers.StringHelpers;
import com.example.bit.R;
import com.example.bit.View.IntentExtras.Constants;
import com.example.bit.databinding.ActivityRegisterUserSecondStepBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class RegisterUserSecondStepActivity extends AppCompatActivity {

    ActivityRegisterUserSecondStepBinding bindingContent;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_register_user_second_step);
        user = (User) getIntent().getSerializableExtra("User");
        bindingContent.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnContinue_click(v);
            }
        });

    }

    private void btnContinue_click(View v) {
        if(argsIsValid()) {
            UserRepository userRepository = new UserRepository(getApplication());
            user = userRepository.insertUser(user, bindingContent.tvPassword.getEditText().getText().toString());
            Intent i = new Intent(RegisterUserSecondStepActivity.this, HomeActivity.class);
            i.putExtra(Constants.USER_INTENT, user);
            startActivity(i);
            finish();
        }
    }

    private boolean argsIsValid() {

        if(bindingContent.tvPassword.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvPassword.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Sua senha não pode ser nula!");
            return false;
        }

        if(bindingContent.tvConfirmPassword.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvConfirmPassword.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Digite a confirmação da senha!");
            return false;
        }

        if(!bindingContent.tvPassword.getEditText().getText().toString().equals(bindingContent.tvConfirmPassword.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "As senhas digitadas devem ser iguais!");
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
}
