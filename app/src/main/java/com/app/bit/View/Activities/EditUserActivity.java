package com.app.bit.View.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.app.bit.DAL.Entities.User;
import com.app.bit.DAL.Repositories.UserRepository;
import com.app.bit.Helpers.StringHelpers;
import com.app.bit.R;
import com.app.bit.View.IntentExtras.Constants;
import com.app.bit.databinding.ActivityEditUserBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import androidx.databinding.DataBindingUtil;

public class EditUserActivity extends Activity {

    ActivityEditUserBinding bindingContent;
    User user;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        bindingContent = DataBindingUtil.setContentView(this, R.layout.activity_edit_user);
        user = (User) getIntent().getSerializableExtra(Constants.USER_INTENT);
        bindingContent.tvName.getEditText().setText(user.getName());
        bindingContent.tvEmail.getEditText().setText(user.getEmail());
        userRepository = new UserRepository(getApplication());

        bindingContent.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(argsIsValid()) {
                    String email = bindingContent.tvEmail.getEditText().getText().toString();
                    String username = bindingContent.tvName.getEditText().getText().toString();

                    if(bindingContent.tvPassword.getEditText().getText() != null && !StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvPassword.getEditText().getText().toString())) {
                        String password = bindingContent.tvPassword.getEditText().getText().toString();
                        user = userRepository.generatePassword(user, password);
                    }

                    user.setName(username);
                    user.setEmail(email);
                    userRepository.update(user);

                    new MaterialAlertDialogBuilder(v.getContext(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Aviso")
                            .setMessage("Alterações feitas com sucesso!")
                            .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(EditUserActivity.this, HomeActivity.class);
                                    i.putExtra(Constants.USER_INTENT, user);
                                    startActivity(i);
                                    finish();
                                }
                            })
                            .create()
                            .show();

                }
            }
        });
    }

    private boolean argsIsValid() {

        if(bindingContent.tvEmail.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvEmail.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Seu email não pode ser nulo!");
            return false;
        }

        if(bindingContent.tvName.getEditText().getText() == null || StringHelpers.isNullEmptyOrWhitespace(bindingContent.tvName.getEditText().getText().toString())) {
            showMaterialMessage("Aviso", "Digite seu nome!");
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
