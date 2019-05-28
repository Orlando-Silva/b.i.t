package com.example.bit.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.View;

import com.example.bit.DAL.Entities.User;
import com.example.bit.Helpers.StringHelpers;
import com.example.bit.R;
import com.example.bit.databinding.ActivityRegisterUserBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class RegisterUserActivity extends AppCompatActivity {

    ActivityRegisterUserBinding bindingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_register_user);

        bindingContent.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnContinue_click(v);
            }
        });


    }

    private void btnContinue_click(View v) {
        if(argsIsValid()) {
            Intent i = new Intent(RegisterUserActivity.this, RegisterUserSecondStepActivity.class);
            User user = makeFirstStepUser();
            i.putExtra("User", user);
            startActivity(i);
        }
    }

    private User makeFirstStepUser() {
        User user = new User();

        user.setName(bindingContent.tvName.getEditText().getText().toString());
        user.setEmail(bindingContent.tvEmail.getEditText().getText().toString());
        user.setLogin(bindingContent.tvLogin.getEditText().getText().toString());

        return user;
    }

    private boolean argsIsValid() {

        if(bindingContent.tvName.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvName.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "O nome não pode ser nulo!");
            return false;
        }

        if(bindingContent.tvEmail.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvEmail.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "O email não pode ser nulo!");
            return false;
        }

        if(bindingContent.tvEmail.getEditText().getText() != null && !bindingContent.tvEmail.getEditText().getText().toString().contains("@")) {
            showMaterialMessage("Aviso", "Insira um email válido!");
            return false;
        }

        if(bindingContent.tvLogin.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvLogin.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "O login não pode ser nulo!");
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
